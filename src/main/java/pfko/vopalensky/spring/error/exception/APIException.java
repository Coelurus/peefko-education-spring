package pfko.vopalensky.spring.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class APIException extends RuntimeException {
    protected final String code;
    protected final String field;
}
