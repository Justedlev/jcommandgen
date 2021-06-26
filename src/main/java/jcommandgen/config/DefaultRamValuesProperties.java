package jcommandgen.config;

import java.io.IOException;
import java.util.Optional;

public class DefaultRamValuesProperties {

    public DefaultRamValuesProperties() {
    }

    public Integer getDefaultMinRamValue() throws IOException {
        return parseInt(JcommandgenProperties.getProperties().getProperty("defaultMinRamValue")).orElse(-1);
    }

    public Integer getDefaultMaxRamValue() throws IOException {
        return parseInt(JcommandgenProperties.getProperties().getProperty("defaultMaxRamValue")).orElse(-1);
    }

    private Optional<Integer> parseInt(String value) {
        return Optional.of(Integer.parseInt(value));
    }

}
