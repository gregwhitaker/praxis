package praxis.client;

public enum PraxisEventType {
    DATA(1);

    private final int value;

    PraxisEventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
