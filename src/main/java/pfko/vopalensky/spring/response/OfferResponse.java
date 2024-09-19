package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Service;
import pfko.vopalensky.spring.model.User;
import lombok.AllArgsConstructor;

import java.util.List;

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
    private User createdBy;

    public OfferResponse(Offer offer) {
        id = offer.getId();
        name = offer.getName();
        cost = offer.getCost();
        services = offer.getServices();
        createdBy = offer.getCreatedBy();
    }
}
