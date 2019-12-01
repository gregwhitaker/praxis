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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import praxis.client.Praxis;
import praxis.client.PraxisBuilder;
import praxis.demo.config.settings.PraxisSettings;

import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableConfigurationProperties({
        PraxisSettings.class
})
public class PraxisConfiguration {

    @Autowired
    Environment environment;

    /**
     * Get an instance of the Praxis client.
     *
     * @return praxis client
     */
    @Bean
    public Praxis praxis(PraxisSettings settings) {
        PraxisBuilder builder = Praxis.builder();

        builder.connect(settings.getHostname(), settings.getPort())
                .application("praxis-demo")
                .heartbeat()
                    .interval(Duration.ofMinutes(1))
                    .build();

        if (environment.getActiveProfiles().length > 0) {
            builder.environment(environment.getActiveProfiles()[0]);
        }

        builder.instance(UUID.randomUUID().toString());

        return builder.build();
    }
}
