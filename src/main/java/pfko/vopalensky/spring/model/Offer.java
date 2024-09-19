package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
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
}
