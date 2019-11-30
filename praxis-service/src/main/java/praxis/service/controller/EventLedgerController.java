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
package praxis.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import praxis.service.service.ledger.EventLedgerService;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Controller responsible for ingesting events into Praxis.
 */
@RestController
public class EventLedgerController {

    private final EventLedgerService eventService;

    @Autowired
    public EventLedgerController(EventLedgerService eventService) {
        this.eventService = eventService;
    }

    /**
     * Receives incoming events.
     *
     * @param body event data
     * @return returns an HTTP 202 if the event was successfully accepted for processing; otherwise an HTTP 400
     */
    @PostMapping("/events")
    public Mono<ResponseEntity> consumeEvent(@RequestBody byte[] body) {
        return eventService.consumeEvent(body)
                .map((Function<Void, ResponseEntity>) aVoid -> ResponseEntity.status(202).build())
                .onErrorReturn(ResponseEntity.badRequest().build());
    }
}
