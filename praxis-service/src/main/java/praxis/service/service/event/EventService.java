package praxis.service.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.data.event.EventDao;
import praxis.service.data.event.model.Event;
import reactor.core.publisher.Mono;

@Component
public class EventService {

    @Autowired
    private EventDao eventDao;

    public Mono<Event> getEvent(String id) {
        return eventDao.findOne(id);
    }
}
