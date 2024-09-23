package pfko.vopalensky.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SupplierTeam extends Creator {
    private final long id;
    private long leaderId;
    private List<Long> members;
}
