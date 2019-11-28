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

@Component
public final class EncryptionKeys {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptionKeys.class);

    private static final String PUBLIC_KEY_NAME = "praxis.pub";
    private static final String PRIVATE_KEY_NAME = "praxis.key";

    @Autowired
    private PraxisSettings settings;

    @Autowired
    @Qualifier("homeDir")
    private Path homeDir;

    private Path publicKeyFile;
    private Path privateKeyFile;
    private JWKSet jwk;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public EncryptionKeys() {
        this.publicKeyFile = homeDir.resolve(PUBLIC_KEY_NAME);
        this.privateKeyFile = homeDir.resolve(PRIVATE_KEY_NAME);

        LOG.debug("Initializing {}", this.getClass().getSimpleName());
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
        X509EncodedKeySpec pubKs = new X509EncodedKeySpec(Files.readAllBytes(publicKeyFile));
        this.publicKey = kf.generatePublic(pubKs);

        // Load private key
        PKCS8EncodedKeySpec pvtKs = new PKCS8EncodedKeySpec(Files.readAllBytes(privateKeyFile));
        this.privateKey = kf.generatePrivate(pvtKs);

        // Load JWKSet
        this.jwk = JWKSet.load(homeDir.resolve(PUBLIC_KEY_NAME).toFile());
    }

    private void store() {
        if (this.publicKey == null || this.privateKey == null) {
            throw new RuntimeException("Cannot store 'null' KeyPair");
        }

        // Store the public key
        try (OutputStream os = Files.newOutputStream(this.homeDir.resolve(PUBLIC_KEY_NAME),
                StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            os.write(this.publicKey.getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Store the private key
        try (OutputStream os = Files.newOutputStream(this.homeDir.resolve(PRIVATE_KEY_NAME),
                StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            os.write(this.privateKey.getEncoded());
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
