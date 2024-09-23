package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User extends Creator {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("name")
    private String name;

    // TEMPORARY DIVISION BASED ON USER X SUPPLIER
    public User(Long id, String userName, String password,
                Status status, String name) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
    }

    public User(Long id, String userName, String password,
                Status status, String name, Long creatorId) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
        this.creatorId = creatorId;
        this.creatorType = CreatorType.INDIVIDUAL;
    }
}
