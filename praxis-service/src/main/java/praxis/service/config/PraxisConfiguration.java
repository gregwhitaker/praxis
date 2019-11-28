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
package praxis.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import praxis.service.config.settings.PraxisSettings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties({
        PraxisSettings.class
})
public class PraxisConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(PraxisConfiguration.class);

    @Autowired
    @Bean(name = "homeDir")
    public Path homeDir(PraxisSettings settings) throws Exception {
        if (Files.notExists(Paths.get(settings.getHomeDirectory()))) {
            Files.createDirectories(Paths.get(settings.getHomeDirectory()));
        }

        return Paths.get(settings.getHomeDirectory());
    }
}
