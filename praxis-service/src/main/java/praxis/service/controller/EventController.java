package praxis.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import praxis.service.service.event.EventService;
import reactor.core.publisher.Mono;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events/{eventId}")
    public Mono<ResponseEntity<Object>> getEvent(@PathVariable("eventId") String eventId) {
        return eventService.getEvent(eventId)
                .map(event -> {
                    return ResponseEntity.ok(null);
                })
                .switchIfEmpty(Mono.fromSupplier(() -> ResponseEntity.notFound().build()));
    }
}
