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
package praxis.service.core.crypto;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import praxis.service.config.settings.PraxisSettings;
import sun.security.rsa.RSAKeyPairGenerator;

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
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Generates, loads, and stores the RSA key pair to use when encrypting and decrypting events.
 */
@Component
public final class EncryptionKeys {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptionKeys.class);

    private static final String PUBLIC_KEY_NAME = "praxis.pem";
    private static final String PRIVATE_KEY_NAME = "praxis.key";

    private final PraxisSettings settings;
    private final Path homeDir;
    private final Path publicKeyPath;
    private final Path privateKeyPath;
    private JWKSet jwkSet;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    @Autowired
    public EncryptionKeys(@Qualifier("homeDir") Path homeDir, PraxisSettings settings) throws Exception {
        this.homeDir = homeDir;
        this.settings = settings;
        this.publicKeyPath = homeDir.resolve(PUBLIC_KEY_NAME);
        this.privateKeyPath = homeDir.resolve(PRIVATE_KEY_NAME);

        LOG.info("Initializing Encryption Keys...");

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

        LOG.info("Encryption Key Initialization Complete");
    }

    /**
     * Returns a JSON Web Key (JWK) document.
     *
     * @return JWK
     */
    public JWKSet getPublicJWK() {
        return jwkSet;
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
        X509EncodedKeySpec pubKs = new X509EncodedKeySpec(loadPem(publicKeyPath));
        this.publicKey = (RSAPublicKey) kf.generatePublic(pubKs);

        // Load private key
        PKCS8EncodedKeySpec pvtKs = new PKCS8EncodedKeySpec(loadPem(privateKeyPath));
        this.privateKey = (RSAPrivateKey) kf.generatePrivate(pvtKs);

        // Load JWKSet
        RSAKey jwk = new RSAKey.Builder(this.publicKey).build();
        this.jwkSet = new JWKSet(jwk);
    }

    private byte[] loadPem(Path path) throws Exception {
        String pem = new String(Files.readAllBytes(path));
        Pattern parse = Pattern.compile("(?m)(?s)^---*BEGIN.*---*$(.*)^---*END.*---*$.*");
        String pemKey = parse.matcher(pem).replaceFirst("$1");

        return Base64.getMimeDecoder().decode(pemKey);
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
        this.publicKey = (RSAPublicKey) kp.getPublic();
        this.privateKey = (RSAPrivateKey) kp.getPrivate();
    }
}
