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
package praxis.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration source that retrieves values from environment variables.
 */
public class EnvironmentConfigSource extends BaseConfigSource {
    private static final Logger LOG = LoggerFactory.getLogger(EnvironmentConfigSource.class);

    @Override
    public String get(String name) {
        LOG.debug("Retrieving configuration from environment variable [name: '{}']", name);
        return System.getenv(name);
    }

    @Override
    public <T> T get(String name, Class<T> clazz) {
        LOG.debug("Retrieving configuration from environment variable [name: '{}', type: '{}'", name, clazz.getTypeName());
        return null;
    }
}
