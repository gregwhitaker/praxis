package praxis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
        "id",
        "correlationId",
        "type",
        "timestamp",
        "application",
        "instance",
        "environment",
        "attributes"
})
public class StartupEvent extends AttributeEvent implements Event {

    private String id;
    private String correlationId;
    private long timestamp;
    private String application;
    private String instance;
    private String environment;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @JsonProperty("type")
    @Override
    public long getType() {
        return EventType.STARTUP.getValue();
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
