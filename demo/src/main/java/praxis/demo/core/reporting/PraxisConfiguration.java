package praxis.demo.core.reporting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import praxis.client.Praxis;

@Configuration
public class PraxisConfiguration {

    @Bean
    public Praxis praxis() {
        return Praxis.builder()
                .hostname("localhost")
                .port(8080)
                .build();
    }
}
