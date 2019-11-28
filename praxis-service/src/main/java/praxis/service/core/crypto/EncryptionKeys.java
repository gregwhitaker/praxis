package praxis.service.core.crypto;

import com.nimbusds.jose.jwk.JWKSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import praxis.service.config.settings.PraxisSettings;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Generates, loads, and stores the RSA key pair to use when encrypting and decrypting events.
 */
@Component
public final class EncryptionKeys {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptionKeys.class);

    private static final String PUBLIC_KEY_NAME = "praxis.pub";
    private static final String PRIVATE_KEY_NAME = "praxis.key";

    private final PraxisSettings settings;
    private final Path homeDir;
    private final Path publicKeyPath;
    private final Path privateKeyPath;
    private JWKSet jwk;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Autowired
    public EncryptionKeys(@Qualifier("homeDir") Path homeDir, PraxisSettings settings) throws Exception {
        this.homeDir = homeDir;
        this.settings = settings;
        this.publicKeyPath = homeDir.resolve(PUBLIC_KEY_NAME);
        this.privateKeyPath = homeDir.resolve(PRIVATE_KEY_NAME);

        LOG.debug("Initializing {}", this.getClass().getSimpleName());

        if (Files.notExists(publicKeyPath) || Files.notExists(privateKeyPath)) {
            // Don't automatically generate encryption keys unless explicitly configured
            if (!settings.isAutogenerateKeys()) {
                throw new RuntimeException("Cannot auto-generate new encryption keys because property 'praxis.autogenerate-keys` is `false`");
            }

            // Generate new keys
            generate();

            // Store the new keys on the file system
            store();
        }

        // Load the keys from the file system
        load();
    }

    /**
     * Returns a JSON Web Key (JWK) document.
     *
     * @return JWK
     */
    public JWKSet getPublicJWK() {
        return jwk;
    }

    /**
     * Gets the public RSA key to use for encryption.
     *
     * @return public key
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Gets the private RSA key to use for decryption.
     *
     * @return private key
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    private void load() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        // Load public key
        X509EncodedKeySpec pubKs = new X509EncodedKeySpec(Files.readAllBytes(publicKeyPath));
        this.publicKey = kf.generatePublic(pubKs);

        // Load private key
        PKCS8EncodedKeySpec pvtKs = new PKCS8EncodedKeySpec(Files.readAllBytes(privateKeyPath));
        this.privateKey = kf.generatePrivate(pvtKs);

        // Load JWKSet
        this.jwk = JWKSet.load(homeDir.resolve(PUBLIC_KEY_NAME).toFile());
    }

    private void store() {
        if (this.publicKey == null || this.privateKey == null) {
            throw new RuntimeException("Cannot store 'null' KeyPair");
        }

        Base64.Encoder encoder = Base64.getEncoder();

        // Store the public key
        try (OutputStream os = Files.newOutputStream(this.publicKeyPath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            os.write("-----BEGIN RSA PUBLIC KEY-----\n".getBytes());
            os.write(encoder.encode(this.publicKey.getEncoded()));
            os.write("\n-----END RSA PUBLIC KEY-----\n".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Store the private key
        try (OutputStream os = Files.newOutputStream(this.privateKeyPath,
                StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            os.write("-----BEGIN RSA PRIVATE KEY-----\n".getBytes());
            os.write(encoder.encode(this.privateKey.getEncoded()));
            os.write("\n-----END RSA PRIVATE KEY-----\n".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generate() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);

        KeyPair kp = kpg.generateKeyPair();
        this.publicKey = kp.getPublic();
        this.privateKey = kp.getPrivate();
    }
}
