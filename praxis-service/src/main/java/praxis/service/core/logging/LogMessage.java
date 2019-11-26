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
