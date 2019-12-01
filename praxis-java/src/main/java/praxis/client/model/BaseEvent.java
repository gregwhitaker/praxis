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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseEvent {
    protected final static ObjectMapper MAPPER = new ObjectMapper();

    protected UUID id;
    protected UUID correlatedId;
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

    public UUID getCorrelatedId() {
        return correlatedId;
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
