package praxis.client.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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
