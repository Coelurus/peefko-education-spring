package pfko.vopalensky.spring.model;

import lombok.Data;

import java.util.List;

@Data
public class SupplierTeam {
    private final long id;
    private long leaderId;
    private List<Long> members;
}
