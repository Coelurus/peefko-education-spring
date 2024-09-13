package pfko.vopalensky.spring.model;

public class Order {
    private long id;
    private Offer offer;
    private User customer;
    private boolean completed;
    private boolean payed;

    public Order(long id, Offer offer, User customer){
        this(id, offer, customer, Boolean.FALSE, Boolean.FALSE);
    }

    public Order(long id, Offer offer, User customer, boolean completed, boolean payed) {
        this.id = id;
        this.offer = offer;
        this.customer = customer;
        this.completed = completed;
        this.payed = payed;
    }

    public long getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public User getCustomer() {
        return customer;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isPayed() {
        return payed;
    }
}
