package praxis.client.model;

import org.junit.Assert;
import org.junit.Test;

public class UserDefinedEventTest {

    @Test
    public void shouldReturnCorrectType() {
        UserDefinedEvent event = new UserDefinedEvent.Builder().build();

        Assert.assertEquals(EventType.USER_DEFINED.getValue(), event.getType());
    }
}
