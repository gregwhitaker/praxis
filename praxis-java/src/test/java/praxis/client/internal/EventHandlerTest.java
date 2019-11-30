package praxis.client.internal;

import org.junit.BeforeClass;
import org.mockserver.mockserver.MockServer;

public class EventHandlerTest {
    // TODO: setup mock server

    @BeforeClass
    public void setup() {
        MockServer mockServer = new MockServer(9090);
    }

    // TODO: validate that the call to praxis is correct
}
