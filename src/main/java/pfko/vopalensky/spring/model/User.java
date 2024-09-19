package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("id")
    private long id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("hashPassword")
    private String hashPassword;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("name")
    private String name;

    public User(long id, String userName, String hashPassword, StatusEnum status, String name) {
        this.id = id;
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.status = status;
        this.name = name;
    }
}
