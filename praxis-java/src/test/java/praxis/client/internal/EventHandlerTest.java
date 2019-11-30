package praxis.client.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.verify.VerificationTimes;
import praxis.client.PraxisConfiguration;
import praxis.client.model.UserDefinedEvent;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class EventHandlerTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this);

    private MockServerClient mockServerClient;

    @Test
    public void shouldCallPraxisService() throws Exception {
        // Setup mock Praxis events endpoint
        mockServerClient
                .when(
                        request("/events")
                                .withMethod("POST")
                )
                .respond(
                        response()
                                .withStatusCode(202)
                );

        ObjectMapper mapper = new ObjectMapper();

        UserDefinedEvent userDefinedEvent = new UserDefinedEvent.Builder().build();

        Event event = new Event();
        event.setWrappedEvent(userDefinedEvent);

        PraxisConfiguration config = new PraxisConfiguration();
        config.setHostname(mockServerClient.remoteAddress().getHostName());
        config.setPort(mockServerClient.remoteAddress().getPort());

        EventHandler eventHandler = new EventHandler(config);
        eventHandler.onEvent(event, 1, true);

        // Ensure that the event handler calls the Praxis endpoint
        mockServerClient
                .verify(
                        request("/events")
                                .withMethod("POST"),
                        VerificationTimes.once()
                );
    }
}
