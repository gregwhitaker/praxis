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

/**
 * Interface that all configuration sources must implement.
 */
public interface ConfigSource {

    /**
     * Get the configuration value by name.
     *
     * @param name configuration name
     * @return the value if it exists; otherwise <code>null</code>
     */
    String get(String name);

    /**
     * Get the configuration value by name and cast to specified type.
     *
     * @param name configuration name
     * @param clazz return type
     * @param <T> return type
     * @return the value if it exists; otherwise <code>null</code>
     */
    <T> T get(String name, Class<T> clazz);
}
