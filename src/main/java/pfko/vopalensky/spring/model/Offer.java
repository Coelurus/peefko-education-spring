package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    @Column(name = "creator_type", nullable = false)
    private CreatorType creatorType;

    @JsonProperty("creator")
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    public Offer(String name,
                 Long cost,
                 List<MyService> services,
                 CreatorType creatorType,
                 Long creatorId) {
        this.name = name;
        this.cost = cost;
        this.services = services;
        this.creatorType = creatorType;
        this.creatorId = creatorId;
    }
}
