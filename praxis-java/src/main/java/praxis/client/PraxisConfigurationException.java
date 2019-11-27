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
import java.util.Set;

/**
 * Exception thrown when the Praxis configuration is invalid.
 */
public class PraxisConfigurationException extends RuntimeException {

    private final Set<ConstraintViolation<PraxisConfiguration>> violations;

    public PraxisConfigurationException(Set<ConstraintViolation<PraxisConfiguration>> violations) {
        super("Invalid Praxis Configuration");
        this.violations = violations;
    }

    public Set<ConstraintViolation<PraxisConfiguration>> getViolations() {
        return violations;
    }
}
