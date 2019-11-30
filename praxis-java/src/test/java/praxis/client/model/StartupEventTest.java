package praxis.client.model;

import org.junit.Assert;
import org.junit.Test;

public class StartupEventTest {

    @Test
    public void shouldReturnCorrectType() {
        StartupEvent event = new StartupEvent.Builder().build();

        Assert.assertEquals(EventType.STARTUP.getValue(), event.getType());
    }
}
