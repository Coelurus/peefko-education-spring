package pfko.vopalensky.spring.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class APIException extends RuntimeException {
    private final String code;
    private final String field;
}
