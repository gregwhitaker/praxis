/**
 * Copyright 2019 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
