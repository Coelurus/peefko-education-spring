package pfko.vopalensky.spring.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Response returned by server when error happens
 */
@AllArgsConstructor
public class ErrorResponse {
    /**
     * All errors that happened
     */
    @JsonProperty("errors")
    private List<ErrorDetail> errors;
}
