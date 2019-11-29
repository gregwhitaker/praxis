package praxis.client;

import java.time.Duration;

public class PraxisHeartbeatBuilder {

    private PraxisClientBuilder clientBuilder;
    private PraxisConfiguration config;

    public PraxisHeartbeatBuilder(PraxisClientBuilder clientBuilder, PraxisConfiguration config) {
        this.clientBuilder = clientBuilder;
        this.config = config;
        this.config.setHeartbeat(new PraxisConfiguration.Heartbeat());
    }

    public PraxisHeartbeatBuilder interval(Duration interval) {
        this.config.getHeartbeat().setInterval(interval);
        return this;
    }

    public PraxisClientBuilder build() {
        return clientBuilder;
    }
}
