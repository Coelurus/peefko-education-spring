package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.NotFoundException;
import pfko.vopalensky.spring.model.SupplierTeam;
import pfko.vopalensky.spring.repository.TeamRepository;
import pfko.vopalensky.spring.response.TeamResponse;
import pfko.vopalensky.spring.response.UserResponse;

import java.util.List;

@Service
public class TeamService {

    private static final String SCOPE = "TEAM";

    private final TeamRepository teamRepository;
    UserService userService;

    @Autowired
    public TeamService(UserService userService, TeamRepository teamRepository) {
        this.userService = userService;
        this.teamRepository = teamRepository;
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

    /**
     * Returns team by id as team responses
     *
     * @param id ID of team to transform to response
     * @return Team response
     */
    public TeamResponse getTeamResponse(Long id) {
        return getTeamResponse(teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SCOPE)));
    }


}
