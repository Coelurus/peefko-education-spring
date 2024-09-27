package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import pfko.vopalensky.spring.model.Status;

@Getter
@Data
@AllArgsConstructor
public class UserResponse implements CreatorResponse {
    @JsonProperty("id")
    private long id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("name")
    private String name;
}
