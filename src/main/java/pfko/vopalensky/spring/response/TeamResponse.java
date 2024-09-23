package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TeamResponse implements CreatorResponse {
    @JsonProperty("id")
    private final long id;
    @JsonProperty("leader")
    private final UserResponse leader;
    @JsonProperty("members")
    private final List<UserResponse> members;


    public TeamResponse(long id, UserResponse leader,
                        List<UserResponse> members) {
        this.id = id;
        this.leader = leader;
        this.members = members;
    }
}
