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
package praxis.service.core.ledger.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of event types.
 */
public enum EventType {
    STARTUP(1),
    SHUTDOWN(2),
    HEARTBEAT(3),
    USER_DEFINED(4);

    private static final Map<Integer, EventType> LOOKUP = new HashMap<>();

    static {
        EnumSet.allOf(EventType.class).forEach(e -> LOOKUP.put(e.getValue(), e));
    }

    private final int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EventType get(int value) {
        return LOOKUP.get(value);
    }
}
