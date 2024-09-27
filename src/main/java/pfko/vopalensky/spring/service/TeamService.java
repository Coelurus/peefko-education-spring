package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.model.SupplierTeam;
import pfko.vopalensky.spring.response.TeamResponse;
import pfko.vopalensky.spring.response.UserResponse;

import java.util.List;

@Service
public class TeamService {
    UserService userService;

    @Autowired
    public TeamService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create team response object from db entity
     *
     * @param team Database entity object
     * @return Response object
     */
    public TeamResponse getTeamResponse(SupplierTeam team) {
        UserResponse leader = userService.getUserResponse(team.getLeader());

        List<UserResponse> members = team.getMembers().stream()
                .map(userService::getUserResponse)
                .toList();
        return new TeamResponse(team.getId(), leader, members);
    }
}
