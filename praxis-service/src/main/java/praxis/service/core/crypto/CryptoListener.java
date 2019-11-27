package praxis.service.core.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import praxis.service.config.settings.PraxisSettings;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Initializes the RSA key pairs needed for encryption and decryption of events.
 */
@Component
public class CryptoListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(CryptoListener.class);

    @Autowired
    private PraxisSettings settings;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final Path homeDir = getHomeDirectory();

        if (isNewEncryptionKeysNeeded(homeDir)) {
            // Don't automatically generate encryption keys unless explicitly configured
            if (!settings.isAutogenerateKeys()) {
                // TODO: Fix this error handling
                throw new RuntimeException("");
            }

            // Delete the Praxis public key if it exists
            try {
                Files.deleteIfExists(homeDir.resolve("praxis.pub"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Delete the Praxis private key if it exists
            try {
                Files.deleteIfExists(homeDir.resolve("praxis.key"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Generate a new RSA key pair
            KeyPair kp = generateKeyPair();

            // Store the RSA key pair on the file system
            storeKeys(kp, homeDir);
        }
    }

    private Path getHomeDirectory() {
        // Creating the Praxis home directory if it does not exist
        if (Files.notExists(Paths.get(settings.getHomeDirectory()))) {
            try {
                Files.createDirectories(Paths.get(settings.getHomeDirectory()));
            } catch (IOException e) {
                // TODO: Fix this exception handling
                throw new RuntimeException("", e);
            }
        }

        return Paths.get(settings.getHomeDirectory());
    }

    private boolean isNewEncryptionKeysNeeded(Path homeDir) {
        return true;
    }

    /**
     *
     * @return
     */
    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);

            return kpg.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param kp
     * @param homeDir
     */
    private void storeKeys(KeyPair kp, Path homeDir) {
        // Store the public key
        try (OutputStream os = Files.newOutputStream(homeDir.resolve("praxis.pub"), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            os.write(kp.getPublic().getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Store the private key
        try (OutputStream os = Files.newOutputStream(homeDir.resolve("praxis.key"), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            os.write(kp.getPrivate().getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
