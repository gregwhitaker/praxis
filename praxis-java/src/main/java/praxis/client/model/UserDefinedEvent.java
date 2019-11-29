package praxis.client.model;

import java.io.IOException;
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
}
