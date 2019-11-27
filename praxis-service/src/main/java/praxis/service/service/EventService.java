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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.core.event.EventProcessor;
import praxis.service.data.event.EventLedgerDao;
import reactor.core.publisher.Mono;

@Component
public class EventService {
    private static final Logger LOG = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventLedgerDao eventLedger;

    @Autowired
    private EventProcessor eventProcessor;

    /**
     *
     * @param data
     * @return
     */
    public Mono<Void> consumeEvent(byte[] data) {
        return eventLedger.save(data)
                .doOnSuccess(eventId -> eventProcessor.schedule(eventId))
                .then();
    }
}
