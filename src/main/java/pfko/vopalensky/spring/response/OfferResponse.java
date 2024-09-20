package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Service;
import pfko.vopalensky.spring.model.UserM;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class OfferResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cost")
    private Long cost;

    @JsonProperty("services")
    private List<Service> services;

    @JsonProperty("created")
    private UserM createdBy;

    public OfferResponse(Offer offer) {
        id = offer.getId();
        name = offer.getName();
        cost = offer.getCost();
        services = offer.getServices();
        createdBy = offer.getCreatedBy();
    }
}
