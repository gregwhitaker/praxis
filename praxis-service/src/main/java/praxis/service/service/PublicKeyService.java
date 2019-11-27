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
package praxis.service.service;

import com.nimbusds.jose.jwk.JWKSet;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.config.settings.EncryptionSettings;
import praxis.service.core.crypto.KeyStoreUtil;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service that manages the public encryption keys used for incoming events.
 */
@Component
public class PublicKeyService {
    private static final Logger LOG = LoggerFactory.getLogger(PublicKeyService.class);

    private static final String DEFAULT_KEYSTORE_NAME = "praxis.keystore";

    @Autowired
    public PublicKeyService(EncryptionSettings settings) {
        init(settings);
    }

    /**
     * Gets the public encryption key information as a JWK set.
     *
     * @return
     */
    public Mono<JWKSet> getKeys() {
        return Mono.fromSupplier(() -> {
           return null;
        });
    }

    /**
     * Initializes this service on startup.
     *
     * @param settings
     */
    private void init(final EncryptionSettings settings) {
        // Checking for keystore directory configuration
        if (StringUtils.isEmpty(settings.getKeystoreDirectory())) {
            // TODO: fix this error handling
            throw new RuntimeException("");
        }

        // Create the keystore directory if it does not exist
        if (Files.notExists(Paths.get(settings.getKeystoreDirectory()))) {
            try {
                LOG.debug("Creating new keystore directory [dir: '{}']", settings.getKeystoreDirectory());
                Files.createDirectories(Paths.get(settings.getKeystoreDirectory()));
            } catch (IOException e) {
                // TODO: fix this error handling
                throw new RuntimeException("", e);
            }
        }

        // Checking for keystore name configuration
        String keystoreName = DEFAULT_KEYSTORE_NAME;
        if (StringUtils.isEmpty(settings.getKeystoreName())) {
            LOG.warn("No property found for 'praxis.encryption.keystore-name'. Defaulting to '{}'", DEFAULT_KEYSTORE_NAME);
        } else {
            keystoreName = settings.getKeystoreName();
        }

        // Create the keystore file if it does not exist
        if (Files.notExists(Paths.get(settings.getKeystoreDirectory(), keystoreName))) {
            LOG.debug("Creating new keystore [dir: '{}', name: '{}']", settings.getKeystoreDirectory(), keystoreName);
            KeyStoreUtil.create(Paths.get(settings.getKeystoreDirectory()), keystoreName);
        }
    }
}
