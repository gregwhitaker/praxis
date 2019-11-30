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

import org.junit.Test;

public class PraxisBuilderTest {

    @Test(expected = PraxisConfigurationException.class)
    public void shouldThrowExceptionWhenNullHostname() {
        Praxis praxis = Praxis.builder().connect(null, 8080)
                .build();
    }

    @Test(expected = PraxisConfigurationException.class)
    public void shouldThrowExceptionWhenEmptyHostname() {
        Praxis praxis = Praxis.builder().connect("", 8080)
                .build();
    }

    @Test(expected = PraxisConfigurationException.class)
    public void shouldThrowExceptionWhenMinimumPortExceeded() {
        Praxis praxis = Praxis.builder().connect("localhost", 0)
                .build();
    }

    @Test(expected = PraxisConfigurationException.class)
    public void shouldThrowExceptionWhenMaximumPortExceeded() {
        Praxis praxis = Praxis.builder().connect("localhost", 65_536)
                .build();
    }
}
