package pfko.vopalensky.spring.repository;

import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.Creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CreatorRepository implements ObjectRepository<Creator> {

    private final List<Creator> creators = new ArrayList<>();

    @Override
    public void store(Creator creator) {
        creators.add(creator);
    }

    @Override
    public Creator get(Long id) {
        return creators.stream()
                .filter(c -> Objects.equals(c.getCreatorId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public Creator delete(Long id) {
        Creator creator = get(id);
        creators.remove(creator);
        return creator;
    }

    @Override
    public List<Creator> findAll() {
        return creators;
    }
}
