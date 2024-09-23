package pfko.vopalensky.spring.model;

import lombok.Data;


@Data
public class MyService {
    private final long id;
    private final String name;
    private final String description;
}
