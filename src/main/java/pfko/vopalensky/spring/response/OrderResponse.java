package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pfko.vopalensky.spring.model.Order;

@Getter
@AllArgsConstructor
public class OrderResponse {
    @JsonProperty("id")
    private final long id;

    @JsonProperty("offerId")
    private long offerId;

    @JsonProperty("customerId")
    private long customerId;

    @JsonProperty("completed")
    private boolean completed;

    @JsonProperty("payed")
    private boolean payed;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.offerId = order.getOfferId();
        this.customerId = order.getCustomerId();
        this.completed = order.isCompleted();
        this.payed = order.isPayed();
    }
}
