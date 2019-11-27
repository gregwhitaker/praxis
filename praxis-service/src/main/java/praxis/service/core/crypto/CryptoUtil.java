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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;

public final class CryptoUtil {
    private static final Logger LOG = LoggerFactory.getLogger(CryptoUtil.class);

    private CryptoUtil() {
        // Prevent direct instantiation
    }

    /**
     *
     * @param directory
     * @param name
     * @return
     * @throws IOException
     */
    public static KeyStore createKeyStore(Path directory, String name, String password) throws Exception {
        // Create the keystore directory if it does not exist
        if (Files.notExists(directory)) {
            LOG.debug("Creating new keystore directory [dir: '{}']", directory.toString());
            Files.createDirectories(directory);
        }

        OutputStream outStream = Files.newOutputStream(directory.resolve(name),
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE);

        KeyStore ks = KeyStore.getInstance("JKS");

        ks.store(outStream, password.toCharArray());

        return ks;
    }

    public static void createKeyPair(String alias, KeyStore keyStore) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);

        KeyPair kp = kpg.generateKeyPair();
    }
}
