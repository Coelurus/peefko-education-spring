package pfko.vopalensky.spring.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pfko.vopalensky.spring.model.CreatorType;

import java.util.List;

@Data
public class OfferRequest {
    private String name;
    private Long cost;
    private List<Long> servicesIds;
    private CreatorType creatorType;
    private Long creatorId;

    public OfferRequest(
            @JsonProperty("name") String name,
            @JsonProperty("cost") Long cost,
            @JsonProperty("services") List<Long> servicesIds,
            @JsonProperty("creator_type") CreatorType creatorType,
            @JsonProperty("creator_id") Long creatorId) {
        this.name = name;
        this.cost = cost;
        this.servicesIds = servicesIds;
        this.creatorType = creatorType;
        this.creatorId = creatorId;
    }
}
