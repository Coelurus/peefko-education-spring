package pfko.vopalensky.spring.error.exception;

public class FieldValidationException extends APIException {
    private static final String DEFAULT_CODE = "INVALID_INPUT";

    public FieldValidationException(final String field) {
        super(DEFAULT_CODE, field);
    }
}
