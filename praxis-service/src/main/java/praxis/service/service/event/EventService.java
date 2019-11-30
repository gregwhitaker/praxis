package praxis.service.service.event;

import org.springframework.stereotype.Component;
import praxis.service.service.event.model.Event;
import reactor.core.publisher.Mono;

@Component
public class EventService {

    public Mono<Event> getEvent(String id) {
        return null;
    }
}
