package jcommandgen.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    private static final Properties PROPERTIES = new Properties();

    public static Properties getProps(String propsFileName) throws IOException {
        File fileProps = new File(String.format("%s.properties", propsFileName)).getAbsoluteFile();
        PROPERTIES.load(new FileReader(fileProps));
        return PROPERTIES;
    }

    public static String getProperty(String propsFileName, String property) throws IOException {
        Properties props = getProps(propsFileName);
        return props.getProperty(property);
    }

}
