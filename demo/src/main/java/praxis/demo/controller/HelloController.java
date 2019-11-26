package praxis.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Endpoint for creating hello messages.
 */
@RestController
public class HelloController {

    /**
     * Returns a hello message.
     *
     * @param name name of the recipient
     * @return a JSON hello message
     */
    @GetMapping(value = "/hello",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hello(@RequestParam(value = "name", required = false, defaultValue = "You") String name) {
        Map<String, String> body = new HashMap<>();
        body.put("message", String.format("Hello, %s!", name));

        return ResponseEntity.ok(body);
    }
}
