package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OrderResponse {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("offer")
    private OfferResponse offer;

    @JsonProperty("customer")
    private UserResponse customer;

    @JsonProperty("completed")
    private boolean completed;

    @JsonProperty("payed")
    private boolean payed;

    public OrderResponse(Long id, OfferResponse offer, UserResponse customer,
                         boolean completed, boolean payed) {
        this.id = id;
        this.offer = offer;
        this.customer = customer;
        this.completed = completed;
        this.payed = payed;
    }
}
