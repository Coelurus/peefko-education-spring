package pfko.vopalensky.spring.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.error.exception.NotFoundException;

import java.util.List;

/**
 * Handles returning better exceptions info
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException(NotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                List.of(new ErrorDetail(exception.getCode(), exception.getField()))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<ErrorResponse> handleFieldValidationException(FieldValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                List.of(new ErrorDetail(ex.getCode(), ex.getField()))
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ignored) {

        ErrorResponse re = new ErrorResponse(
                List.of(new ErrorDetail("AUTHENTICATION", "PAGE ACCESS"))
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}
