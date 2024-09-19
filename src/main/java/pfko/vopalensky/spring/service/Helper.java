package pfko.vopalensky.spring.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pfko.vopalensky.spring.repository.ObjectRepository;

public class Helper {

    public static <T> ResponseEntity<T> objectCreator(T object, ObjectRepository<T> repository) {
        try {
            repository.store(object);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private Helper() {
    }
}
