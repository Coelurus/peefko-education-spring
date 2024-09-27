package pfko.vopalensky.spring.error.exception;

public class AuthenticationException extends APIException {
    private static final String DEFAULT_CODE = "NOT AUTHENTICATED";

    public AuthenticationException(String field) {
        super(DEFAULT_CODE, field);
    }
}
