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
package praxis.service.core.event;

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
import praxis.service.core.logging.LogMessage;

@Component
public class EventProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(EventProcessor.class);

    private final RingBuffer<ProcessEvent> eventBuffer;

    @Autowired
    public EventProcessor(EventProcessorHandler handler) {
        Disruptor<ProcessEvent> disruptor = new Disruptor<>(
                ProcessEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        disruptor.handleEventsWith(handler);

        this.eventBuffer = disruptor.start();
    }

    /**
     * Adds the event id to the ring buffer for later processing.
     *
     * @param eventId
     */
    public void schedule(long eventId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(LogMessage.builder()
                    .withMessage("Event scheduled for processing")
                    .withData("eventId", eventId)
                    .build());
        }

        long seq = eventBuffer.next();

        ProcessEvent eventToProcess = eventBuffer.get(seq);
        eventToProcess.setEventId(eventId);

        eventBuffer.publish(seq);
    }

    /**
     * Event wrapper that holds event ids to process in the LMAX buffer.
     */
    static class ProcessEvent {
        static final EventFactory<ProcessEvent> EVENT_FACTORY = ProcessEvent::new;

        private long eventId;

        ProcessEvent() { }

        long getEventId() {
            return eventId;
        }

        void setEventId(long eventId) {
            this.eventId = eventId;
        }
    }
}
