package praxis.client.internal.eventhandling;

import com.lmax.disruptor.EventHandler;

public class PublishEventHandler implements EventHandler<EventWrapper> {

    @Override
    public void onEvent(EventWrapper event, long sequence, boolean endOfBatch) throws Exception {

    }
}
