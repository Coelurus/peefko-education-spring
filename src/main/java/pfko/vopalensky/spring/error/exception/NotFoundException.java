package pfko.vopalensky.spring.error.exception;

import lombok.Getter;

/**
 * Exception thrown on 404
 */
@Getter
public class NotFoundException extends APIException {
    private static final String DEFAULT_CODE = "NOT_FOUND";

    public NotFoundException(final String field) {
        super(DEFAULT_CODE, field);
    }
}
