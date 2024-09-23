package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pfko.vopalensky.spring.model.Creator;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.repository.UserRepository;

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
    private List<Long> services;

    @JsonProperty("created")
    private Creator createdBy;

    public OfferResponse(Offer offer,
                         UserRepository userRepository) {
        id = offer.getId();
        name = offer.getName();
        cost = offer.getCost();
        services = offer.getServicesIds();
        createdBy = userRepository.get(offer.getCreatorId());
    }
}
