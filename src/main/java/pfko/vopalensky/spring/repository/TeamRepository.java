package pfko.vopalensky.spring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.SupplierTeam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class TeamRepository implements ObjectRepository<SupplierTeam> {

    @Autowired
    public TeamRepository(CreatorRepository creatorRepository) {
        SupplierTeam team = new SupplierTeam(0L, 0L,
                Arrays.asList(0L, 1L), 0L);
        store(team);
        creatorRepository.store(team);
    }

    private final List<SupplierTeam> teams = new ArrayList<>();

    @Override
    public void store(SupplierTeam supplierTeam) {
        teams.add(supplierTeam);
    }

    @Override
    public SupplierTeam get(Long id) {
        return teams.stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public SupplierTeam delete(Long id) {
        SupplierTeam supplierTeam = get(id);
        teams.remove(supplierTeam);
        return supplierTeam;
    }

    @Override
    public List<SupplierTeam> findAll() {
        return teams;
    }
}
