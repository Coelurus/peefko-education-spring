package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import pfko.vopalensky.spring.model.StatusEnum;
import pfko.vopalensky.spring.model.UserM;

@Getter
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

    public UserResponse(UserM user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.hashPassword = user.getPassword();
        this.status = user.getStatus();
        this.name = user.getName();
    }
}
