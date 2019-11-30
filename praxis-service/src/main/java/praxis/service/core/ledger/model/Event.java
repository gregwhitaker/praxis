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
package praxis.service.core.ledger.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Data parsed from the incoming events and added to the database.
 */
public class Event {

    public UUID id;
    public UUID correlatedId;
    public long timestamp;
    public int type;
    public String application;
    public String instance;
    public String environment;
    public final Map<String, Object> attributes = new HashMap<>();

    public Event() {
        // Noop
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCorrelatedId() {
        return correlatedId;
    }

    public void setCorrelatedId(UUID correlatedId) {
        this.correlatedId = correlatedId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
