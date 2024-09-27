package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("userName")
    @Column(name = "username", nullable = false)
    private String userName;

    @JsonProperty("password")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty("status")
    @Column(name = "status", nullable = false)
    private String status;

    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("on_team")
    @ManyToOne
    @JoinColumn(name = "team_id")
    private SupplierTeam team;

    // TEMPORARY DIVISION BASED ON USER X SUPPLIER
    public User(Long id, String userName, String password,
                String status, String name) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
    }
}
