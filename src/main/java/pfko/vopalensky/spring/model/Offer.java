package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("cost")
    @Column(name = "cost", nullable = false)
    private Long cost;

    @JsonProperty("services")
    @ManyToMany
    private List<MyService> services;

    @JsonProperty("creatorType")
    @Column(name = "creator_type", nullable = false)
    private String creatorType;

    @JsonProperty("creator")
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    public Offer(@JsonProperty("id") Long id,
                 @JsonProperty("name") String name,
                 @JsonProperty("cost") Long cost,
                 @JsonProperty("services") List<MyService> services,
                 @JsonProperty("creator") Long creatorId) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.services = services;
        this.creatorId = creatorId;
    }
}
