package praxis.demo.core.phonehome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PhoneHomeListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(PhoneHomeListener.class);

    private License license;

    public PhoneHomeListener() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(License.class);

        try {
            license = reader.readValue(getClass().getClassLoader().getResourceAsStream("license.json"));
        } catch (IOException e) {
            LOG.error("Unable to read license.json");
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Phoning Home...");
    }
}
