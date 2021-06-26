package jcommandgen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jcommandgen.api.lang.AppLanguage;

import java.io.File;
import java.io.IOException;

import static jcommandgen.api.Constants.DEFAULT_LANGUAGE_FILE_NAME;

public class LanguageProperties {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public LanguageProperties() {
    }

    public AppLanguage getLanguage() throws IOException {
        String fileLangName = String.format(DEFAULT_LANGUAGE_FILE_NAME, JcommandgenProperties.getProperties().get("lang"));
        File fileLang = new File(fileLangName).getAbsoluteFile();
        return MAPPER.readValue(fileLang, AppLanguage.class);
    }

}
