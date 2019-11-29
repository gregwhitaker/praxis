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
package praxis.client;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import praxis.client.internal.Event;
import praxis.client.internal.EventHandler;
import praxis.client.model.BaseEvent;
import praxis.client.model.HeartbeatEvent;
import praxis.client.model.ShutdownEvent;
import praxis.client.model.StartupEvent;
import praxis.client.model.UserDefinedEvent;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Praxis client.
 */
public final class Praxis {
    private static final Logger LOG = LoggerFactory.getLogger(Praxis.class);

    /**
     * Fluent builder for creating a new instance of the {@link Praxis} client.
     *
     * @return the {@link PraxisBuilder} to use for creating a new Praxis client
     */
    public static PraxisBuilder builder() {
        return new PraxisBuilder();
    }

    private final PraxisConfiguration config;
    private final RingBuffer<Event> ringBuffer;

    Praxis(final PraxisConfiguration config) {
        this.config = config;
        this.ringBuffer = createRingBuffer(config);

        if (config.isHeartbeatEnabled()) {
            startHeartbeat();
        }
    }

    public void send(StartupEvent event) {
        sendInternal(event);
    }

    public void send(HeartbeatEvent event) {
        sendInternal(event);
    }

    public void send(ShutdownEvent event) {
        sendInternal(event);
    }

    public void send(UserDefinedEvent event) {
        sendInternal(event);
    }

    private void sendInternal(BaseEvent event) {
        if (event != null) {
            long seq = ringBuffer.next();

            Event evt = ringBuffer.get(seq);
            evt.setWrappedEvent(event);

            ringBuffer.publish(seq);
        }
    }

    /**
     *
     * @param config
     * @return
     */
    private RingBuffer<Event> createRingBuffer(PraxisConfiguration config) {
        Disruptor<Event> disruptor = new Disruptor<>(
                Event::new,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        disruptor.handleEventsWith(new EventHandler(config));

        return disruptor.start();
    }

    private void startHeartbeat() {
        LOG.info("Starting application heartbeat...");

        StartupEvent startupEvent = new StartupEvent.Builder()
                .application(config.getApplication())
                .instance(config.getInstance())
                .environment(config.getEnvironment())
                .build();

        send(startupEvent);

        Flux.interval(config.getHeartbeat().getInterval())
                .map(tick -> new HeartbeatEvent.Builder()
                        .correlatedEvent(startupEvent)
                        .build())
                .doOnEach(signal -> LOG.debug("Sending Application Heartbeat"))
                .subscribe(this::send);
    }
}
