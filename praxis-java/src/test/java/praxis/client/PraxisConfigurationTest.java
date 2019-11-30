package praxis.client;

import org.junit.Assert;
import org.junit.Test;

public class PraxisConfigurationTest {

    @Test
    public void shouldReturnHttpUrlWhenSslDisabled() {
        PraxisConfiguration config = new PraxisConfiguration();
        config.setHostname("localhost");
        config.setPort(8080);

        Assert.assertTrue(config.getBaseUrl().startsWith("http://"));
    }

    @Test
    public void shouldReturnHttpsUrlWhenSslEnabled() {
        PraxisConfiguration config = new PraxisConfiguration();
        config.setHostname("localhost");
        config.setPort(8080);
        config.setSslEnabled(true);

        Assert.assertTrue(config.getBaseUrl().startsWith("https://"));
    }

    @Test
    public void shouldDefaultPortTo80() {
        PraxisConfiguration config = new PraxisConfiguration();
        config.setHostname("localhost");

        Assert.assertEquals(80, config.getPort());
    }
}
