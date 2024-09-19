package pfko.vopalensky.spring.model;

import lombok.Data;


@Data
public class Service {
    private final long id;
    private String name;
    private String description;
}
