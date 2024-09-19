package pfko.vopalensky.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.repository.OrderRepository;
import pfko.vopalensky.spring.response.OrderResponse;

import java.util.List;

@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Get all orders
     *
     * @return All orders on board
     */
    @GetMapping(value = "/order", produces = "application/json")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        List<OrderResponse> orders = orderRepository.getResponses();
        return ResponseEntity.ok(orders);
    }

    /**
     * Place a new order for an offer on a board
     *
     * @param order new order
     * @return newly created order
     */
    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody Order order) {
        try {
            orderRepository.store(order);
            return new ResponseEntity<>(new OrderResponse(order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Find purchase order by ID
     *
     * @param orderId ID of order to be fetched
     * @return Found order
     */
    @GetMapping(value = "/order/{orderId}", produces = "application/json")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") Long orderId) {
        try {
            Order found = orderRepository.get(orderId);
            if (found != null) {
                return new ResponseEntity<>(new OrderResponse(found), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing order with form data
     *
     * @param orderId   ID of an order that needs to be updated
     * @param completed State of completion of an order
     * @param payed     Payment status of an order
     * @return Updated order object
     */
    @PostMapping(value = "/order/{orderId}", produces = "application/json")
    public ResponseEntity<OrderResponse> updateOrderWithForm(
            @PathVariable("orderId") Long orderId,
            @RequestParam(value = "complete", required = false) Boolean completed,
            @RequestParam(value = "payed", required = false) Boolean payed) {
        try {
            Order order = orderRepository.get(orderId);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            order.setCompleted(completed);
            order.setPayed(payed);
            return new ResponseEntity<>(new OrderResponse(order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete purchase order by order ID
     *
     * @param orderId ID of the order that needs to be deleted
     */
    @DeleteMapping(value = "/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepository.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
