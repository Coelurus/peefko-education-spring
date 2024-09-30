package pfko.vopalensky.spring.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderRequest {
    private Long offerId;
    private Long userId;
    private Boolean payed;
    private Boolean completed;

    public OrderRequest(
            @JsonProperty("offer_id") Long offerId,
            @JsonProperty("customer_id") Long userId,
            @JsonProperty("payed") Boolean payed,
            @JsonProperty("completed") Boolean completed) {
        this.offerId = offerId;
        this.userId = userId;
        this.payed = payed;
        this.completed = completed;
    }
}
