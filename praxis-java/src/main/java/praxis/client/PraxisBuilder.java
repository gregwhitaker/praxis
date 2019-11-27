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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Praxis client builder.
 */
public class PraxisBuilder {

    private final PraxisConfiguration config = new PraxisConfiguration();

    /**
     * Sets the hostname of the Praxis service to which to connect this client.
     *
     * @param hostname
     * @return
     */
    public PraxisBuilder hostname(String hostname) {
        this.config.setHostname(hostname);
        return this;
    }

    /**
     * Sets the port of the Praxis service to which to connect this client.
     *
     * @param port
     * @return
     */
    public PraxisBuilder port(int port) {
        this.config.setPort(port);
        return this;
    }

    /**
     * Creates an instance of the {@link Praxis} client.
     *
     * @return a fully configured instance of the Praxis client
     */
    public Praxis build() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PraxisConfiguration>> violations = validator.validate(config);

        if (!violations.isEmpty()) {
            throw new PraxisConfigurationException(violations);
        }

        return new Praxis(config);
    }
}
