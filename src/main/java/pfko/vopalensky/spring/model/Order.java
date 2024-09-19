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
                 @JsonProperty("customerId") long customerId) {
        this(id, offerId, customerId, Boolean.FALSE, Boolean.FALSE);
    }

    public Order(long id, long offerId, long customerId, boolean completed, boolean payed) {
        this.id = id;
        this.offerId = offerId;
        this.customerId = customerId;
        this.completed = completed;
        this.payed = payed;
    }
}
