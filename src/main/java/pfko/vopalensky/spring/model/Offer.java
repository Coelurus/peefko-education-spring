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
    private List<Long> servicesIds;

    @JsonProperty("creator")
    private Long creatorId;

    public Offer(@JsonProperty("id") Long id,
                 @JsonProperty("name") String name,
                 @JsonProperty("cost") Long cost,
                 @JsonProperty("services") List<Long> servicesIds,
                 @JsonProperty("creator") Long creatorId) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.servicesIds = servicesIds;
        this.creatorId = creatorId;
    }
}
