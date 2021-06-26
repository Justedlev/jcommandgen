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
public class CodesDescriptions {

    private String incorrect_input_number;
    private String min_less_than_default_min;
    private String min_more_than_default_max;
    private String max_less_than_default_max;
    private String max_more_than_default_max;
    private String entered_min_less_than_default_min;
    private String entered_min_more_than_default_max;
    private String entered_max_less_than_default_min;
    private String entered_max_more_than_default_max;
    private String engine_file_not_selected;
    private String file_generated;
    private String file_not_generated;
    private String file_modified;
    private String file_not_modified;
    private String accepted;
    private String not_accepted;

}
