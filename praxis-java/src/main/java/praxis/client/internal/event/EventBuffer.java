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
package praxis.client.internal.event;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import praxis.client.PraxisConfiguration;

public class EventBuffer {

    private final RingBuffer<EventWrapper> ringBuffer;

    public EventBuffer(PraxisConfiguration config) {
        // Configure ring buffer for outgoing events
        Disruptor<EventWrapper> disruptor = new Disruptor<>(
                EventWrapper::new,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        // Assign event handlers to the ring buffer
        disruptor.handleEventsWith(
                new RawDataEventHandler(this),
                new EncodedDataEventHandler(config, this));

        // Start ring buffer for outgoing events
        this.ringBuffer = disruptor.start();
    }

    public void publish(Event event) {
        // Getting the next ring buffer memory location
        long seq = this.ringBuffer.next();

        // Wrapping event so it can be put into the ring buffer
        EventWrapper eventWrapper = this.ringBuffer.get(seq);
        eventWrapper.setType(event.getType());
        eventWrapper.setEvent(event);

        // Publishing event to the ring buffer
        this.ringBuffer.publish(seq);
    }
}
