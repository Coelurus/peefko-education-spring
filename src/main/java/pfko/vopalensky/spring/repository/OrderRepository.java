package pfko.vopalensky.spring.repository;

import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderRepository implements ObjectRepository<Order> {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public void store(Order order) {
        orders.add(order);
    }

    @Override
    public Order get(Long id) {
        return orders.stream()
                .filter(o -> Objects.equals(o.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public Order delete(Long id) {
        Order order = get(id);
        orders.removeIf(o -> Objects.equals(o.getId(), id));
        return order;
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }
}
