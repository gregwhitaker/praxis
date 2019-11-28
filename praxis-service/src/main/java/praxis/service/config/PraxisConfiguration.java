package praxis.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import praxis.service.config.settings.PraxisSettings;

import java.nio.file.Path;

@Configuration
@EnableConfigurationProperties({
        PraxisSettings.class
})
public class PraxisConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(PraxisConfiguration.class);

    @Bean(name = "homeDir")
    public Path homeDir() {
        return null;
    }
}
