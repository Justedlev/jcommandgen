package jcommandgen.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static jcommandgen.api.Constants.DEFAULT_VALUES_PROPS_FILE_NAME;

public class JcommandgenProperties {

    private static final Properties PROPERTIES = new Properties();

    public static Properties getProperties() throws IOException {
        File fileProps = new File(DEFAULT_VALUES_PROPS_FILE_NAME).getAbsoluteFile();
        PROPERTIES.load(new FileReader(fileProps));
        return PROPERTIES;
    }

}
