package pfko.vopalensky.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.MyService;

@Repository
public interface ServiceRepository extends JpaRepository<MyService, Long> {
}
