package jcommandgen.api.lang;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppLanguage {

    private String error_msg;
    private String app_info;
    private String select_engine;
    private String change_engine;
    private String select_new_engine;
    private String engine_not_selected;
    private String eula_button;
    private String chose_engine_button;
    private String generate_button;
    private String with_gui;
    private String ignore_20_seconds;
    private String min_ram;
    private String max_ram;
    private String cmd_file_name;
    private CodesDescriptions codes_descriptions;

}
