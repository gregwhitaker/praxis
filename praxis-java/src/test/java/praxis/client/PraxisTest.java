package praxis.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.verify.VerificationTimes;
import praxis.client.model.StartupEvent;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class PraxisTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this);

    private MockServerClient mockServerClient;

    @Test
    public void shouldSendStartupEvent() throws Exception {
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

        // Configure Praxis client
        Praxis praxis = Praxis.builder()
                .connect(mockServerClient.remoteAddress().getHostName(), mockServerClient.remoteAddress().getPort())
                .build();

        // Send the event
        StartupEvent startupEvent = new StartupEvent.Builder().build();
        praxis.send(startupEvent);

        // Hokey, but we need to wait for the response to get through
        // async processing in the lmax buffer
        Thread.sleep(1_000);

        // Verify the event
        mockServerClient
                .verify(
                        request("/events")
                                .withMethod("POST")
                                .withBody(MAPPER.writerFor(StartupEvent.class).writeValueAsString(startupEvent)),
                        VerificationTimes.once()
                );
    }
}
