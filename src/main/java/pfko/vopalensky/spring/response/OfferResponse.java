package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OfferResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cost")
    private Long cost;

    @JsonProperty("services")
    private List<ServiceResponse> services;

    @JsonProperty("created")
    private CreatorResponse createdBy;

    public OfferResponse(Long id, String name, Long cost,
                         List<ServiceResponse> services,
                         CreatorResponse createdBy) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.services = services;
        this.createdBy = createdBy;
    }
}
