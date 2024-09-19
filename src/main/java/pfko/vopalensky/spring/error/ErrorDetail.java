package pfko.vopalensky.spring.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Concrete information about one error
 */
@AllArgsConstructor
@Getter
public class ErrorDetail {
    /**
     * What happened
     */
    @JsonProperty("code")
    private String code;
    /**
     * Where it happened
     */
    @JsonProperty("scope")
    private String scope;
}
