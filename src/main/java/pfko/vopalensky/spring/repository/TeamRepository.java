package pfko.vopalensky.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.SupplierTeam;

@Repository
public interface TeamRepository extends JpaRepository<SupplierTeam, Long> {
}
