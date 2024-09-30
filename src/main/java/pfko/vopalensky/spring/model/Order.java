package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private long id;

    @JsonProperty("offerId")
    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @JsonProperty("customerId")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @JsonProperty("completed")
    @Column(name = "completed", nullable = false)
    private boolean completed;

    @JsonProperty("payed")
    @Column(name = "payed", nullable = false)
    private boolean payed;

    public Order(@JsonProperty("offerId") Offer offer,
                 @JsonProperty("customerId") User customer,
                 @JsonProperty("completed") boolean completed,
                 @JsonProperty("payed") boolean payed) {
        this.offer = offer;
        this.customer = customer;
        this.completed = completed;
        this.payed = payed;
    }
}
