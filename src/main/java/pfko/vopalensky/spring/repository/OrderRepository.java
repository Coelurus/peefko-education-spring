package pfko.vopalensky.spring.repository;

import org.springframework.stereotype.Repository;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.response.OrderResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderRepository implements ObjectRepository<Order> {

    private final List<Order> orders = new ArrayList<>();

    public OrderRepository() {
        orders.add(new Order(0L, 1L, 0L, false, false));
        orders.add(new Order(1L, 2L, 1L, false, false));
    }

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

    @Override
    public List<OrderResponse> getResponses() {
        return orders.stream().map(OrderResponse::new).toList();
    }
}
