package pfko.vopalensky.spring.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pfko.vopalensky.spring.repository.ObjectRepository;

public class Helper {
    public static final String ACCEPT_TYPE = "application/json";
    public static final String ACCEPT_HEADER = "Accept";

    public static <T> ResponseEntity<T>
    objectCreator(T object,
                  HttpServletRequest request,
                  ObjectRepository<T> repository) {

        String accept = request.getHeader(ACCEPT_HEADER);
        if (accept != null && accept.contains(ACCEPT_TYPE)) {
            try {
                repository.store(object);
                return new ResponseEntity<>(object, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Helper() {
    }
}
