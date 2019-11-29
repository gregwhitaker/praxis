package praxis.client.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseEvent {

    protected static ObjectMapper MAPPER = new ObjectMapper();

    protected UUID id;
    protected long timestamp;
    protected String application;
    protected String instance;
    protected String environment;
    protected final Map<String, Object> attributes = new HashMap<>();

    public abstract long getType();

    public abstract byte[] toBytes() throws IOException;

    public UUID getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getApplication() {
        return application;
    }

    public String getInstance() {
        return instance;
    }

    public String getEnvironment() {
        return environment;
    }
}
