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
package praxis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import praxis.client.PraxisClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Endpoint for creating hello messages.
 */
@RestController
public class HelloController {

    @Autowired
    private PraxisClient praxis;

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
