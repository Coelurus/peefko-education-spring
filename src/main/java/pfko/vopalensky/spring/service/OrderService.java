package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.error.exception.NotFoundException;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.repository.OrderRepository;
import pfko.vopalensky.spring.response.OrderResponse;

import java.util.List;

@Service
public class OrderService {

    private static final String SCOPE = "Order";

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Get all orders
     *
     * @return All orders in db
     */
    public ResponseEntity<List<OrderResponse>> getOrders() {
        List<OrderResponse> orders = orderRepository.getResponses();
        return ResponseEntity.ok(orders);
    }

    /**
     * Save new order into db
     *
     * @param order Order to be saved
     * @return Response object with new order
     */
    public ResponseEntity<OrderResponse> placeOrder(Order order) {
        try {
            orderRepository.store(order);
            return new ResponseEntity<>(new OrderResponse(order), HttpStatus.OK);
        } catch (Exception e) {
            throw new FieldValidationException(SCOPE);
        }
    }

    /**
     * Find order by id in db
     *
     * @param orderId ID to find order by
     * @return found order
     */
    public ResponseEntity<OrderResponse> getOrderById(Long orderId) {
        try {
            Order found = orderRepository.get(orderId);
            if (found != null) {
                return new ResponseEntity<>(new OrderResponse(found), HttpStatus.OK);
            } else {
                throw new NotFoundException(SCOPE);
            }
        } catch (Exception e) {
            throw new FieldValidationException(SCOPE);
        }
    }

    /**
     * Update an existing order
     *
     * @param orderId   ID of an order that needs to be updated
     * @param completed State of completion of an order
     * @param payed     Payment status of an order
     * @return Response object of updated order
     */
    public ResponseEntity<OrderResponse> updateOrderWithForm(Long orderId, Boolean completed, Boolean payed) {
        try {
            Order order = orderRepository.get(orderId);
            if (order == null) {
                throw new NotFoundException(SCOPE);
            }
            order.setCompleted(completed);
            order.setPayed(payed);
            return new ResponseEntity<>(new OrderResponse(order), HttpStatus.OK);
        } catch (Exception e) {
            throw new FieldValidationException(SCOPE);
        }
    }

    /**
     * Delete order from db
     *
     * @param orderId ID of order to be deleted
     * @return ok response
     */
    public ResponseEntity<Void> deleteOrder(Long orderId) {
        orderRepository.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


