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
