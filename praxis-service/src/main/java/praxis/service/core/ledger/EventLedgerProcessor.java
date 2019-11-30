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
package praxis.service.core.ledger;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Ring buffer that holds events in the ledger that are ready for processing.
 */
@Component
public class EventLedgerProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(EventLedgerProcessor.class);

    private final RingBuffer<ProcessLedgerEvent> eventBuffer;

    @Autowired
    public EventLedgerProcessor(EventLedgerHandler handler) {
        Disruptor<ProcessLedgerEvent> disruptor = new Disruptor<>(
                ProcessLedgerEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        disruptor.handleEventsWith(handler);

        this.eventBuffer = disruptor.start();
    }

    /**
     * Adds the ledger id to the ring buffer for later processing.
     *
     * @param ledgerId ledger identifier
     */
    public void schedule(UUID ledgerId) {
        LOG.debug("Ledger event scheduled for processing: '{}'", ledgerId);

        long seq = eventBuffer.next();

        ProcessLedgerEvent eventToProcess = eventBuffer.get(seq);
        eventToProcess.setLedgerId(ledgerId);

        eventBuffer.publish(seq);
    }

    /**
     * Event wrapper that holds event ids to process in the LMAX buffer.
     */
    static class ProcessLedgerEvent {
        static final EventFactory<ProcessLedgerEvent> EVENT_FACTORY = ProcessLedgerEvent::new;

        private UUID ledgerId;

        UUID getLedgerId() {
            return ledgerId;
        }

        void setLedgerId(UUID ledgerId) {
            this.ledgerId = ledgerId;
        }
    }
}
