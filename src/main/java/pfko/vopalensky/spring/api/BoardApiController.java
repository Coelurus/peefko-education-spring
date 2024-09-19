package pfko.vopalensky.spring.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.repository.OfferRepository;
import pfko.vopalensky.spring.repository.OrderRepository;

import java.util.List;

@RestController
public class BoardApiController {

    private final OrderRepository orderRepository;
    private final HttpServletRequest request;
    private final OfferRepository offerRepository;

    @Autowired
    public BoardApiController(OfferRepository offerRepository, OrderRepository orderRepository, HttpServletRequest request) {
        this.orderRepository = orderRepository;
        this.offerRepository = offerRepository;
        this.request = request;
    }


    /**
     * Place a new order for an offer on a board
     *
     * @param order new order
     * @return newly created order
     */
    @PostMapping(value = "/order")
    public ResponseEntity<Order> placeOrder(Order order) {
        return Helper.objectCreator(order, request, orderRepository);
    }

    /**
     * Find purchase order by ID
     *
     * @param orderId ID of order to be fetched
     * @return Found order
     */
    @GetMapping(value = "/order/{orderId}")
    public ResponseEntity<Order> getOrderById(Long orderId) {
        String accept = request.getHeader(Helper.ACCEPT_HEADER);
        if (accept != null && accept.contains(Helper.ACCEPT_TYPE)) {
            try {
                Order found = orderRepository.get(orderId);
                if (found != null) {
                    return new ResponseEntity<>(found, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Update an existing order with form data
     *
     * @param orderId   ID of an order that needs to be updated
     * @param completed State of completion of an order
     * @param payed     Payment status of an order
     * @return Updated order object
     */
    @PostMapping(value = "/order/{orderId}")
    public ResponseEntity<Order> updateOrderWithForm(Long orderId, Boolean completed, Boolean payed) {
        String accept = request.getHeader(Helper.ACCEPT_HEADER);
        if (accept != null && accept.contains(Helper.ACCEPT_TYPE)) {
            try {
                Order order = orderRepository.get(orderId);
                if (order == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                order.setCompleted(completed);
                order.setPayed(payed);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }

    /**
     * Delete purchase order by order ID
     *
     * @param orderId ID of the order that needs to be deleted
     */
    @DeleteMapping(value = "/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(Long orderId) {
        orderRepository.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
