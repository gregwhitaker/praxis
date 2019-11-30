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
package praxis.client.model;

import org.junit.Assert;
import org.junit.Test;

public class StartupEventTest {

    @Test
    public void shouldReturnCorrectType() {
        StartupEvent event = new StartupEvent.Builder().build();

        Assert.assertEquals(EventType.STARTUP.getValue(), event.getType());
    }

    @Test
    public void shouldSetFieldsWhenCorrelatedEventAdded() {
        UserDefinedEvent userDefinedEvent = new UserDefinedEvent.Builder()
                .application("foo")
                .instance("fooInstance")
                .environment("test")
                .build();

        StartupEvent event = new StartupEvent.Builder()
                .correlatedEvent(userDefinedEvent)
                .build();

        Assert.assertEquals(userDefinedEvent.getId(), event.getCorrelatedId());
        Assert.assertEquals("foo", event.getApplication());
        Assert.assertEquals("fooInstance", event.getInstance());
        Assert.assertEquals("test", event.getEnvironment());
    }
}
