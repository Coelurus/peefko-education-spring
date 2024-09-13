package pfko.vopalensky.spring.model;

import java.util.List;

public class Offer {
    private Long id;
    private String name;
    private Long cost;
    private List<Service> services;
    private User createdBy;

    public Offer(Long id, String name, Long cost){
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCost() {
        return cost;
    }

    public List<Service> getServices() {
        return services;
    }

    public User getCreatedBy() {
        return createdBy;
    }
}
