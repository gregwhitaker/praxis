package praxis.client.model;

import org.junit.Assert;
import org.junit.Test;

public class ShutdownEventTest {

    @Test
    public void shouldReturnCorrectType() {
        ShutdownEvent event = new ShutdownEvent.Builder().build();

        Assert.assertEquals(EventType.SHUTDOWN.getValue(), event.getType());
    }
}
