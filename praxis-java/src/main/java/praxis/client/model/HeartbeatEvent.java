package praxis.client.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeartbeatEvent extends BaseEvent {

    private HeartbeatEvent(UUID id,
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
        return EventType.HEARTBEAT.getValue();
    }

    @Override
    public byte[] toBytes() throws IOException {
        return MAPPER.writerFor(HeartbeatEvent.class).writeValueAsBytes(this);
    }

    /**
     *
     */
    public static class Builder {

        private String application = null;
        private String instance = null;
        private String environment = null;
        private final Map<String, Object> attributes = new HashMap<>();

        public Builder attribute(String key, Object value) {
            this.attributes.put(key, value);
            return this;
        }

        public HeartbeatEvent build() {
            return new HeartbeatEvent(UUID.randomUUID(),
                    System.currentTimeMillis(),
                    application,
                    instance,
                    environment,
                    attributes);
        }
    }
}
