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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

/**
 * Praxis client configuration.
 */
@Valid
public final class PraxisConfiguration {

    @NotBlank(message = "Hostname is required")
    private String hostname;

    @Min(value = 0, message = "Port number must be between 0 and 65535")
    @Max(value = 65_535, message = "Port number must be between 0 and 65535")
    private int port = 80;

    private boolean sslEnabled = false;
    private String application;
    private String instance;
    private String environment;
    private Heartbeat heartbeat;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isHeartbeatEnabled() {
        return getHeartbeat() != null;
    }

    public Heartbeat getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Heartbeat heartbeat) {
        this.heartbeat = heartbeat;
    }

    /**
     *
     */
    public static final class Heartbeat {

        private Duration interval = Duration.ofMinutes(1);

        public Duration getInterval() {
            return interval;
        }

        public void setInterval(Duration interval) {
            this.interval = interval;
        }
    }
}
