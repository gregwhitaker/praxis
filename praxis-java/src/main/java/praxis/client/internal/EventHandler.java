package praxis.client.internal;

import praxis.client.PraxisConfiguration;

public class EventHandler implements com.lmax.disruptor.EventHandler<Event> {

    public EventHandler(PraxisConfiguration config) {

    }

    @Override
    public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception {

    }
}
