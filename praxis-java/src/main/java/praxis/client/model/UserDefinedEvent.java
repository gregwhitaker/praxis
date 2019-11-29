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

public class UserDefinedEvent extends BaseEvent {

    private UserDefinedEvent(UUID id,
                             UUID correlatedId,
                             long timestamp,
                             String application,
                             String instance,
                             String environment,
                             Map<String, Object> attributes) {
        this.id = id;
        this.correlatedId = correlatedId;
        this.timestamp = timestamp;
        this.application = application;
        this.instance = instance;
        this.environment = environment;
        this.attributes.putAll(attributes);
    }

    @Override
    public long getType() {
        return EventType.USER_DEFINED.getValue();
    }

    @Override
    public byte[] toBytes() throws IOException {
        return MAPPER.writerFor(UserDefinedEvent.class).writeValueAsBytes(this);
    }

    public static class Builder {

        private UUID correlatedId = null;
        private String application = null;
        private String instance = null;
        private String environment = null;
        private final Map<String, Object> attributes = new HashMap<>();

        public UserDefinedEvent.Builder correlatedEvent(BaseEvent event) {
            this.correlatedId = event.getId();
            this.application = event.getApplication();
            this.instance = event.getInstance();
            this.environment = event.getEnvironment();
            return this;
        }

        public UserDefinedEvent.Builder correlatedEvent(UUID eventId) {
            this.correlatedId = eventId;
            return this;
        }

        public UserDefinedEvent.Builder application(String application) {
            this.application = application;
            return this;
        }

        public UserDefinedEvent.Builder instance(String instance) {
            this.instance = instance;
            return this;
        }

        public UserDefinedEvent.Builder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public UserDefinedEvent.Builder attribute(String key, Object value) {
            this.attributes.put(key, value);
            return this;
        }

        public UserDefinedEvent.Builder userEventType(long userEventType) {
            attribute("userEventType", userEventType);
            return this;
        }

        public UserDefinedEvent build() {
            return new UserDefinedEvent(UUID.randomUUID(),
                    correlatedId,
                    System.currentTimeMillis(),
                    application,
                    instance,
                    environment,
                    attributes);
        }
    }
}
