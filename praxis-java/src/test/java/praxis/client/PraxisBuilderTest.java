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
