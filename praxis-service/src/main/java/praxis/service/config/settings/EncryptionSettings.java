package praxis.service.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "praxis.encryption")
public class EncryptionSettings {

    private String keystoreDirectory;
    private String keystoreName;

    public String getKeystoreName() {
        return keystoreName;
    }

    public void setKeystoreName(String keystoreName) {
        this.keystoreName = keystoreName;
    }

    public String getKeystoreDirectory() {
        return keystoreDirectory;
    }

    public void setKeystoreDirectory(String keystoreDirectory) {
        this.keystoreDirectory = keystoreDirectory;
    }
}
