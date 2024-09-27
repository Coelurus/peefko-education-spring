package pfko.vopalensky.spring.repository;

import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OfferRepository implements ObjectRepository<Offer> {
    private final List<Offer> offers = new ArrayList<>();

    @Override
    public void store(Offer offer) {
        offers.add(offer);
    }

    @Override
    public Offer get(Long id) {
        return offers.stream()
                .filter(o -> Objects.equals(o.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public Offer delete(Long id) {
        Offer offer = get(id);
        offers.removeIf(o -> Objects.equals(o.getId(), id));
        return offer;
    }

    @Override
    public List<Offer> findAll() {
        return offers;
    }

}
