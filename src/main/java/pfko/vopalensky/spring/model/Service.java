package pfko.vopalensky.spring.model;

public class Service {
    private long id;
    private String name;
    private String description;

    public Service(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
