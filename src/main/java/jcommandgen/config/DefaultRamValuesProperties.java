package jcommandgen.config;

import java.io.IOException;
import java.util.Optional;

public class DefaultRamValuesProperties {

    private static final String DEF_RAM_PATH = "jcommandgen";

    public Integer getDefaultMinRamValue() throws IOException {
        return parseInt(PropertiesLoader.getProperty(DEF_RAM_PATH, "defaultMinRamValue")).orElse(-1);
    }

    public Integer getDefaultMaxRamValue() throws IOException {
        return parseInt(PropertiesLoader.getProperty(DEF_RAM_PATH, "defaultMaxRamValue")).orElse(-1);
    }

    private Optional<Integer> parseInt(String value) {
        return Optional.of(Integer.parseInt(value));
    }

}
