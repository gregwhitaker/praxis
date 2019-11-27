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
