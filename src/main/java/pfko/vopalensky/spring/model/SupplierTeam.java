package pfko.vopalensky.spring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierTeam extends Creator {
    private final Long id;
    private final Long leaderId;
    private final
    List<Long> members;

    public SupplierTeam(Long id, Long leaderId, List<Long> members,
                        Long creatorId) {
        this.id = id;
        this.leaderId = leaderId;
        this.members = members;
        this.creatorId = creatorId;
        this.creatorType = CreatorType.TEAM;
    }
}
