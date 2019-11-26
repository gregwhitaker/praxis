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

/**
 * Praxis client builder.
 */
public class PraxisBuilder {

    private final PraxisConfiguration config = new PraxisConfiguration();

    /**
     * Sets the hostname of the Praxis service to which to connect this client.
     *
     * @param hostname
     * @return
     */
    public PraxisBuilder hostname(String hostname) {
        this.config.setHostname(hostname);
        return this;
    }

    /**
     * Sets the port of the Praxis service to which to connect this client.
     *
     * @param port
     * @return
     */
    public PraxisBuilder port(int port) {
        this.config.setPort(port);
        return this;
    }

    public Praxis build() {
        return new Praxis(config);
    }
}
