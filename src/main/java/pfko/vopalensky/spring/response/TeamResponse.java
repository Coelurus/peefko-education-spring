package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import pfko.vopalensky.spring.model.SupplierTeam;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.UserRepository;

import java.util.List;

public class TeamResponse {
    @JsonProperty("id")
    private final long id;
    @JsonProperty("leader")
    private final User leader;
    @JsonProperty("members")
    private final List<User> members;


    public TeamResponse(SupplierTeam supplierTeam,
                        UserRepository userRepository) {
        this.id = supplierTeam.getId();
        this.leader = userRepository.get(supplierTeam.getLeaderId());
        this.members = supplierTeam.getMembers()
                .stream().map(userRepository::get).toList();
    }
}
