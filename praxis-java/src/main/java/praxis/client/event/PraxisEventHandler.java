package praxis.client.event;

import com.lmax.disruptor.EventHandler;

public class PraxisEventHandler implements EventHandler<PraxisEvent> {

    @Override
    public void onEvent(PraxisEvent event, long sequence, boolean endOfBatch) throws Exception {

    }
}
