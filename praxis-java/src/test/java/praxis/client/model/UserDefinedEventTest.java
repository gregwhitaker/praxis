package praxis.client.model;

import org.junit.Assert;
import org.junit.Test;

public class UserDefinedEventTest {

    @Test
    public void shouldReturnCorrectType() {
        UserDefinedEvent event = new UserDefinedEvent.Builder().build();

        Assert.assertEquals(EventType.USER_DEFINED.getValue(), event.getType());
    }

    @Test
    public void shouldAddUserEventTypeToAttributes() {
        UserDefinedEvent event = new UserDefinedEvent.Builder()
                .userEventType(123)
                .build();

        Assert.assertEquals(123L, event.attributes.get("userEventType"));
    }

    @Test
    public void shouldSetFieldsWhenCorrelatedEventAdded() {
        StartupEvent startupEvent = new StartupEvent.Builder()
                .application("foo")
                .instance("fooInstance")
                .environment("test")
                .build();

        UserDefinedEvent event = new UserDefinedEvent.Builder()
                .correlatedEvent(startupEvent)
                .userEventType(123)
                .build();

        Assert.assertEquals(startupEvent.getId(), event.getCorrelatedId());
        Assert.assertEquals("foo", event.getApplication());
        Assert.assertEquals("fooInstance", event.getInstance());
        Assert.assertEquals("test", event.getEnvironment());
    }
}
