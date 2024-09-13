package pfko.vopalensky.spring.model;

import java.util.List;

public class SupplierTeam {
    private long id;
    private User leader;
    private List<User> members;

    public SupplierTeam(long id, User leader, List<User> members) {
        this.id = id;
        this.leader = leader;
        this.members = members;
    }

    public long getId() {
        return id;
    }

    public User getLeader() {
        return leader;
    }

    public List<User> getMembers() {
        return members;
    }
}
