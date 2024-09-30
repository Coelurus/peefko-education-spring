package pfko.vopalensky.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
