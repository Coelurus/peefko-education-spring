package pfko.vopalensky.spring.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Order;

import java.util.List;

@RestController
public interface BoardApi {


    /**
     * Place a new order for an offer on a board
     *
     * @param order new order
     * @return newly created order
     */
    @PostMapping(value = "/order")
    ResponseEntity<Order> placeOrder(@RequestBody Order order);

    /**
     * Find purchase order by ID
     *
     * @param orderId ID of order to be fetched
     * @return Found order
     */
    @GetMapping(value = "/order/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable("orderId") Long orderId);

    /**
     * Update an existing order with form data
     *
     * @param orderId   ID of an order that needs to be updated
     * @param completed State of completion of an order
     * @param payed     Payment status of an order
     * @return Updated order object
     */
    @PostMapping(value = "/order/{orderId}")
    ResponseEntity<Order> updateOrderWithForm(
            @PathVariable("orderId") Long orderId,
            @RequestParam(value = "complete", required = false)
            Boolean completed,
            @RequestParam(value = "payed", required = false)
            Boolean payed);

    /**
     * Delete purchase order by order ID
     *
     * @param orderId ID of the order that needs to be deleted
     */
    @DeleteMapping(value = "/order/{orderId}")
    ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId);
}
