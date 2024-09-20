package pfko.vopalensky.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserM {
    @JsonProperty("id")
    private long id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("name")
    private String name;

    public UserM(long id, String userName, String password, StatusEnum status, String name) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.name = name;
    }
}
