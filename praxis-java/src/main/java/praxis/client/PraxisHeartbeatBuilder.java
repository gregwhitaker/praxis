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

import java.time.Duration;

public class PraxisHeartbeatBuilder {

    private PraxisBuilder clientBuilder;
    private PraxisConfiguration config;

    public PraxisHeartbeatBuilder(PraxisBuilder clientBuilder, PraxisConfiguration config) {
        this.clientBuilder = clientBuilder;
        this.config = config;
        this.config.setHeartbeat(new PraxisConfiguration.HeartbeatConfiguration());
    }

    public PraxisHeartbeatBuilder interval(Duration interval) {
        this.config.getHeartbeat().setInterval(interval);
        return this;
    }

    public PraxisBuilder build() {
        return clientBuilder;
    }
}
