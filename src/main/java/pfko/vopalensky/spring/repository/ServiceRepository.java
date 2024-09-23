package pfko.vopalensky.spring.repository;

import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.MyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ServiceRepository implements ObjectRepository<MyService> {
    public ServiceRepository() {
        store(new MyService(0L, "NAP", "Like sleeping"));
        store(new MyService(1L, "COFFEE SERVICE", "I drink all your milk"));
        store(new MyService(2L, "DESK INSPECTION", "Dont even ask"));
    }

    private final List<MyService> services = new ArrayList<>();

    @Override
    public void store(MyService myService) {
        services.add(myService);
    }

    @Override
    public MyService get(Long id) {
        return services.stream()
                .filter(s -> Objects.equals(s.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public MyService delete(Long id) {
        MyService myService = get(id);
        services.remove(myService);
        return myService;
    }

    @Override
    public List<MyService> findAll() {
        return List.of();
    }
}
