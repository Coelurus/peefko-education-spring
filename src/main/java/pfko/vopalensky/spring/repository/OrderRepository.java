package pfko.vopalensky.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
