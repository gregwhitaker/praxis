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
package praxis.service.core.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds json log messages for easy parsing by log services such as splunk.
 */
public class LogMessage {

    public static JsonBuilder builder() {
        return new JsonBuilder();
    }

    public static class JsonBuilder {
        private static final ObjectMapper MAPPER = new ObjectMapper();

        private String message;
        private Map<String, Object> data;

        public JsonBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public JsonBuilder withMessage(String message, Object... replacements) {
            String newMessage = message;
            for (Object replacement : replacements) {
                newMessage = newMessage.replaceFirst("\\{\\}", replacement.toString());
            }

            this.message = newMessage;
            return this;
        }

        public JsonBuilder withData(String key, Object value) {
            if (this.data == null) {
                this.data = new HashMap<>();
            }

            this.data.put(key, value);
            return this;
        }

        public JsonBuilder withData(Map<String, Object> newData) {
            if (this.data == null) {
                this.data = new HashMap<>();
            }

            this.data.putAll(newData);
            return this;
        }

        public String build() {
            ObjectNode rootNode = MAPPER.createObjectNode();
            rootNode.put("message", message);

            if (data != null) {
                ObjectNode dataNode = MAPPER.createObjectNode();

                data.forEach((k, v) -> dataNode.put(k, v.toString()));

                rootNode.set("data", dataNode);
            }

            try {
                return MAPPER.writeValueAsString(rootNode);
            } catch (JsonProcessingException e) {
                assert false : "This should never happen";
                return "Error processing log line [message: '" + e.getMessage() + "']";
            }
        }
    }
}
