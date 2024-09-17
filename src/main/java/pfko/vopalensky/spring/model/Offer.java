package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Offer {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cost")
    private Long cost;

    @JsonProperty("services")
    private List<Service> services;

    @JsonProperty("created")
    private User createdBy;

    public Offer(Long id, String name, Long cost) {
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

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }
}
