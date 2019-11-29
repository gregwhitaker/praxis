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
import java.util.function.Supplier;

/**
 * Praxis client builder.
 */
public class PraxisClientBuilder {

    private final PraxisConfiguration config = new PraxisConfiguration();

    public PraxisClientBuilder connect(String hostname, int port) {
        return this.connect(hostname, port, false);
    }

    public PraxisClientBuilder connect(String hostname, int port, boolean enableSSL) {
        this.config.setHostname(hostname);
        this.config.setPort(port);
        this.config.setSslEnabled(enableSSL);
        return this;
    }

    public PraxisHeartbeatBuilder heartbeat() {
        return new PraxisHeartbeatBuilder(this, this.config);
    }

    public PraxisClientBuilder application(String name) {
        this.config.setApplication(name);
        return this;
    }

    public PraxisClientBuilder instance(String instance) {
        this.config.setInstance(instance);
        return this;
    }

    public PraxisClientBuilder environment(String environment) {
        this.config.setEnvironment(environment);
        return this;
    }

    /**
     * Creates an instance of the {@link PraxisClient} client.
     *
     * @return a fully configured instance of the Praxis client
     */
    public PraxisClient build() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PraxisConfiguration>> violations = validator.validate(config);

        if (!violations.isEmpty()) {
            throw new PraxisConfigurationException(violations);
        }

        return new PraxisClient(config);
    }
}
