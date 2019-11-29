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
package praxis.client.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShutdownEvent extends BaseEvent {

    private ShutdownEvent(UUID id,
                          long timestamp,
                          String application,
                          String instance,
                          String environment,
                          Map<String, Object> attributes) {
        this.id = id;
        this.timestamp = timestamp;
        this.application = application;
        this.instance = instance;
        this.environment = environment;
        this.attributes.putAll(attributes);
    }

    @Override
    public long getType() {
        return EventType.SHUTDOWN.getValue();
    }

    @Override
    public byte[] toBytes() throws IOException {
        return MAPPER.writerFor(ShutdownEvent.class).writeValueAsBytes(this);
    }

    public static class Builder {

        private String application = null;
        private String instance = null;
        private String environment = null;
        private final Map<String, Object> attributes = new HashMap<>();

        public ShutdownEvent.Builder application(String application) {
            this.application = application;
            return this;
        }

        public ShutdownEvent.Builder instance(String instance) {
            this.instance = instance;
            return this;
        }

        public ShutdownEvent.Builder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public ShutdownEvent.Builder attribute(String key, Object value) {
            this.attributes.put(key, value);
            return this;
        }

        public ShutdownEvent build() {
            return new ShutdownEvent(UUID.randomUUID(),
                    System.currentTimeMillis(),
                    application,
                    instance,
                    environment,
                    attributes);
        }
    }
}
