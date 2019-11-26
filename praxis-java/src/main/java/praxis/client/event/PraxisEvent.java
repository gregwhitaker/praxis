package praxis.client.event;

import com.lmax.disruptor.EventFactory;

public class PraxisEvent {
    public static final EventFactory<PraxisEvent> EVENT_FACTORY = PraxisEvent::new;
}
