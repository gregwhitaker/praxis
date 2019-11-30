package praxis.client.model;

import org.junit.Assert;
import org.junit.Test;

public class HeartbeatEventTest {

    @Test
    public void shouldReturnCorrectType() {
        HeartbeatEvent event = new HeartbeatEvent.Builder().build();

        Assert.assertEquals(EventType.HEARTBEAT.getValue(), event.getType());
    }
}
