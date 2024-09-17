package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public long getId() {
        return id;
    }

    public long getOfferId() {
        return offerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setOffer(long offer) {
        this.offerId = offer;
    }

    public void setCustomer(long customer) {
        this.customerId = customer;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
