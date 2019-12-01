package praxis.service.data.event.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

public class Event {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Event from(final ResultSet rs) throws Exception {
        Event event = new Event();
        event.setId(rs.getObject("evt_id", UUID.class));
        event.setCorrelationId(rs.getObject("evt_corr_id", UUID.class));
        event.setType(rs.getInt("evt_type"));
        event.setTimestamp(rs.getTimestamp("evt_ts"));
        event.setProcessTimestamp(rs.getTimestamp("evt_process_ts"));
        event.setApplication(rs.getString("evt_app"));
        event.setInstance(rs.getString("evt_ins"));
        event.setEnvironment(rs.getString("evt_env"));
        event.setAttributes(MAPPER.readerFor(Map.class).readValue(rs.getBytes("evt_attrs")));

        return event;
    }

    private UUID id;
    private UUID correlationId;
    private long type;
    private Timestamp timestamp;
    private Timestamp processTimestamp;
    private String application;
    private String instance;
    private String environment;
    private Map<String, Object> attributes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getProcessTimestamp() {
        return processTimestamp;
    }

    public void setProcessTimestamp(Timestamp processTimestamp) {
        this.processTimestamp = processTimestamp;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
