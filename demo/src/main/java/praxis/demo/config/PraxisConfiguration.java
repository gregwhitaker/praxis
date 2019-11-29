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
package praxis.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import praxis.client.PraxisClient;
import praxis.client.PraxisHeartbeatBuilder;

import java.time.Duration;

@Configuration
public class PraxisConfiguration {

    @Autowired
    Environment environment;

    @Bean
    public PraxisClient praxis() {
        return PraxisClient.builder()
                .connect("localhost", 8080)
                .application("demo")
                .heartbeat()
                    .interval(Duration.ofMinutes(1))
                    .build()
                .build();
    }
}
