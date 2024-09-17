package pfko.vopalensky.spring.model;

import java.util.List;

public class SupplierTeam {
    private final long id;
    private long leaderId;
    private List<Long> members;

    public SupplierTeam(long id, long leaderId, List<Long> members) {
        this.id = id;
        this.leaderId = leaderId;
        this.members = members;
    }

    public long getId() {
        return id;
    }

    public long getLeader() {
        return leaderId;
    }

    public List<Long> getMembers() {
        return members;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }
}
