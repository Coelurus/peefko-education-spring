package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Order {

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

    public Order(@JsonProperty("id") long id,
                 @JsonProperty("offerId") long offerId,
                 @JsonProperty("customerId") long customerId,
                 @JsonProperty("completed") boolean completed,
                 @JsonProperty("payed") boolean payed) {
        this.id = id;
        this.offerId = offerId;
        this.customerId = customerId;
        this.completed = completed;
        this.payed = payed;
    }
}
