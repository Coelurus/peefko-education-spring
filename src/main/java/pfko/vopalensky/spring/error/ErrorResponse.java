package pfko.vopalensky.spring.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Response returned by server when error happens
 */
@AllArgsConstructor
@Getter
public class ErrorResponse {
    /**
     * All errors that happened
     */
    @JsonProperty("errors")
    private List<ErrorDetail> errors;
}
