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
package praxis.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Praxis client.
 */
public final class Praxis {
    private static final Logger LOG = LoggerFactory.getLogger(Praxis.class);

    /**
     * Fluent builder for creating a new instance of the {@link Praxis} client.
     *
     * @return the {@link PraxisBuilder} to use for creating a new Praxis client
     */
    public static PraxisBuilder builder() {
        return new PraxisBuilder();
    }

    private final PraxisConfiguration config;

    Praxis(final PraxisConfiguration config) {
        this.config = config;
    }
}
