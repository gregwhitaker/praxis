package praxis.client;

import java.time.Duration;

public class PraxisHeartbeatBuilder {

    private PraxisBuilder clientBuilder;
    private PraxisConfiguration config;

    public PraxisHeartbeatBuilder(PraxisBuilder clientBuilder, PraxisConfiguration config) {
        this.clientBuilder = clientBuilder;
        this.config = config;
        this.config.setHeartbeat(new PraxisConfiguration.HeartbeatConfiguration());
    }

    public PraxisHeartbeatBuilder interval(Duration interval) {
        this.config.getHeartbeat().setInterval(interval);
        return this;
    }

    public PraxisBuilder build() {
        return clientBuilder;
    }
}
