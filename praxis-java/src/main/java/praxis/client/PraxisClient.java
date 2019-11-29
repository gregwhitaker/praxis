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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import praxis.client.internal.event.EventBuffer;
import praxis.client.internal.event.RawDataEvent;

/**
 * Praxis client.
 */
public final class PraxisClient {
    private static final Logger LOG = LoggerFactory.getLogger(PraxisClient.class);

    /**
     * Fluent builder for creating a new instance of the {@link PraxisClient} client.
     *
     * @return the {@link PraxisClientBuilder} to use for creating a new Praxis client
     */
    public static PraxisClientBuilder builder() {
        return new PraxisClientBuilder();
    }

    private final PraxisConfiguration config;
    private final EventBuffer eventBuffer;

    PraxisClient(final PraxisConfiguration config) {
        this.config = config;
        this.eventBuffer = new EventBuffer(config);
    }

//    public void send(StartupEvent event) {
//
//    }
//
//    public void send(HeartbeatEvent event) {
//
//    }
//
//    public void send(ShutdownEvent event) {
//
//    }
//
//    public void send(UserDefinedEvent event) {
//
//    }

    /**
     *
     * @param data
     * @param datatype
     * @param <T>
     */
    public <T> void event(T data, Class<T> datatype) {
        eventBuffer.publish(new RawDataEvent(data, datatype));
    }
}
