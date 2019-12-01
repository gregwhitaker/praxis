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
package praxis.service.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import praxis.service.core.util.PraxisDateFormat;
import praxis.service.data.event.model.Event;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
        "id",
        "correlationId",
        "type",
        "timestamp",
        "processTimestamp",
        "application",
        "instance",
        "environment",
        "attributes"
})
public class GetEventResponse {

    public static GetEventResponse from(final Event event) {
        GetEventResponse response = new GetEventResponse();
        response.setId(event.getId().toString());

        if (event.getCorrelationId() != null) {
            response.setCorrelationId(event.getCorrelationId().toString());
        }

        response.setType(event.getType());
        response.setTimestamp(PraxisDateFormat.format(event.getTimestamp()));

        if (event.getProcessTimestamp() != null) {
            response.setProcessTimestamp(PraxisDateFormat.format(event.getProcessTimestamp()));
        }

        response.setApplication(event.getApplication());
        response.setInstance(event.getInstance());
        response.setEnvironment(event.getEnvironment());
        response.setAttributes(event.getAttributes());

        return response;
    }

    private String id;
    @JsonProperty("correlation_id") private String correlationId;
    private long type;
    private String timestamp;
    @JsonProperty("process_timestamp") private String processTimestamp;
    private String application;
    private String instance;
    private String environment;
    private Map<String, Object> attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProcessTimestamp() {
        return processTimestamp;
    }

    public void setProcessTimestamp(String processTimestamp) {
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
