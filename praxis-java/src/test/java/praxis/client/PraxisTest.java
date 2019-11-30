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
package praxis.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.verify.VerificationTimes;
import praxis.client.model.HeartbeatEvent;
import praxis.client.model.ShutdownEvent;
import praxis.client.model.StartupEvent;
import praxis.client.model.UserDefinedEvent;

import java.time.Duration;

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

    @Test
    public void shouldSendHearbeatEvent() throws Exception {
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
        HeartbeatEvent heartbeatEvent = new HeartbeatEvent.Builder().build();
        praxis.send(heartbeatEvent);

        // Hokey, but we need to wait for the response to get through
        // async processing in the lmax buffer
        Thread.sleep(1_000);

        // Verify the event
        mockServerClient
                .verify(
                        request("/events")
                                .withMethod("POST")
                                .withBody(MAPPER.writerFor(HeartbeatEvent.class).writeValueAsString(heartbeatEvent)),
                        VerificationTimes.once()
                );
    }

    @Test
    public void shouldSendShutdownEvent() throws Exception {
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
        ShutdownEvent shutdownEvent = new ShutdownEvent.Builder().build();
        praxis.send(shutdownEvent);

        // Hokey, but we need to wait for the response to get through
        // async processing in the lmax buffer
        Thread.sleep(1_000);

        // Verify the event
        mockServerClient
                .verify(
                        request("/events")
                                .withMethod("POST")
                                .withBody(MAPPER.writerFor(ShutdownEvent.class).writeValueAsString(shutdownEvent)),
                        VerificationTimes.once()
                );
    }

    @Test
    public void shouldUserDefinedEvent() throws Exception {
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
        UserDefinedEvent userDefinedEvent = new UserDefinedEvent.Builder().build();
        praxis.send(userDefinedEvent);

        // Hokey, but we need to wait for the response to get through
        // async processing in the lmax buffer
        Thread.sleep(1_000);

        // Verify the event
        mockServerClient
                .verify(
                        request("/events")
                                .withMethod("POST")
                                .withBody(MAPPER.writerFor(UserDefinedEvent.class).writeValueAsString(userDefinedEvent)),
                        VerificationTimes.once()
                );
    }

    @Test
    public void shouldSendMultipleHeartbeatEvents() throws Exception {
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
                .heartbeat()
                    .interval(Duration.ofMillis(100))
                    .build()
                .build();

        // Hokey, but we need to wait for the response to get through
        // async processing in the lmax buffer
        Thread.sleep(1_000);

        // Verify the event
        mockServerClient
                .verify(
                        request("/events")
                                .withMethod("POST"),
                        VerificationTimes.atLeast(5)
                );

        mockServerClient.reset();
    }
}
