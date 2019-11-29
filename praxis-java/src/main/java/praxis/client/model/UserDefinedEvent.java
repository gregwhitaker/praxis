package praxis.client.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDefinedEvent extends BaseEvent {

    private UserDefinedEvent(UUID id,
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
        return EventType.USER_DEFINED.getValue();
    }

    @Override
    public byte[] toBytes() throws IOException {
        return MAPPER.writerFor(UserDefinedEvent.class).writeValueAsBytes(this);
    }

    public static class Builder {

        private String application = null;
        private String instance = null;
        private String environment = null;
        private final Map<String, Object> attributes = new HashMap<>();

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
                    System.currentTimeMillis(),
                    application,
                    instance,
                    environment,
                    attributes);
        }
    }
}
