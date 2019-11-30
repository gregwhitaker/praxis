/**
 * Copyright 2019 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package praxis.service.service;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.core.event.EventLedgerProcessor;
import praxis.service.data.event.EventLedgerDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Service that handles incoming events.
 */
@Component
public class EventLedgerService {
    private static final Logger LOG = LoggerFactory.getLogger(EventLedgerService.class);

    private final EventLedgerDao eventLedger;
    private final EventLedgerProcessor eventProcessor;

    @Autowired
    public EventLedgerService(EventLedgerDao eventLedger,
                              EventLedgerProcessor eventProcessor) {
        this.eventLedger = eventLedger;
        this.eventProcessor = eventProcessor;

        startOrphanRecordProcessing();
    }

    /**
     * Consumes and stores the event in the event ledger for future processing.
     *
     * @param data event data
     * @return
     */
    public Mono<Void> consumeEvent(byte[] data) {
        return eventLedger.save(data)
                .doOnSuccess(eventProcessor::schedule)
                .then();
    }

    /**
     * Starts a process in the background for collecting and processing any missed events in the ledger.
     */
    private void startOrphanRecordProcessing() {
        Flux.interval(Duration.ofSeconds(10), Duration.ofMinutes(1))
                .map(tick -> eventLedger.findUnprocessedEvents(100))
                .flatMap((Function<Mono<List<UUID>>, Publisher<List<UUID>>>) listMono -> listMono)
                .flatMapSequential((Function<List<UUID>, Publisher<UUID>>) Flux::fromIterable)
                .doOnEach(uuidSignal -> eventProcessor.schedule(uuidSignal.get()))
                .subscribeOn(Schedulers.elastic())
                .subscribe();
    }
}
