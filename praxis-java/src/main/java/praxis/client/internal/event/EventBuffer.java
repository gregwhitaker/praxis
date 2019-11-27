package praxis.client.internal.event;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import praxis.client.PraxisConfiguration;

public class EventBuffer {

    private final RingBuffer<EventWrapper> ringBuffer;

    public EventBuffer(PraxisConfiguration config) {
        // Configure ring buffer for outgoing events
        Disruptor<EventWrapper> disruptor = new Disruptor<>(
                EventWrapper::new,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        // Assign event handlers to the ring buffer
        disruptor.handleEventsWith(new RawDataEventHandler(this),
                new EncodedDataEventHandler(config, this));

        // Start ring buffer for outgoing events
        this.ringBuffer = disruptor.start();
    }

    public void publish(Event event) {
        // Getting the next ring buffer memory location
        long seq = this.ringBuffer.next();

        // Wrapping event so it can be put into the ring buffer
        EventWrapper eventWrapper = this.ringBuffer.get(seq);
        eventWrapper.setType(event.getType());
        eventWrapper.setEvent(event);

        // Publishing event to the ring buffer
        this.ringBuffer.publish(seq);
    }

    public void publish(long seq) {
        this.ringBuffer.publish(seq);
    }
}
