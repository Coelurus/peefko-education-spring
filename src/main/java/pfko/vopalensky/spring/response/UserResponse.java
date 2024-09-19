package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pfko.vopalensky.spring.model.StatusEnum;
import pfko.vopalensky.spring.model.User;

@Data
public class UserResponse {
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

    public UserResponse(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.hashPassword = user.getHashPassword();
        this.status = user.getStatus();
        this.name = user.getName();
    }
}
