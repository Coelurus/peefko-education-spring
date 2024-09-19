package pfko.vopalensky.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.response.OrderResponse;
import pfko.vopalensky.spring.service.OrderService;

import java.util.List;

@RestController
public class OrderApiController {

    private final OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get all orders
     *
     * @return All orders on board
     */
    @GetMapping(value = "/order", produces = "application/json")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return orderService.getOrders();
    }

    /**
     * Place a new order for an offer on a board
     *
     * @param order new order
     * @return newly created order
     */
    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    /**
     * Find purchase order by ID
     *
     * @param orderId ID of order to be fetched
     * @return Found order
     */
    @GetMapping(value = "/order/{orderId}", produces = "application/json")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
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
        return orderService.updateOrderWithForm(orderId, completed, payed);
    }

    /**
     * Delete purchase order by order ID
     *
     * @param orderId ID of the order that needs to be deleted
     */
    @DeleteMapping(value = "/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
