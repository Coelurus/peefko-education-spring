package pfko.vopalensky.spring.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.repository.OfferRepository;
import pfko.vopalensky.spring.repository.OrderRepository;

import java.util.List;

@RestController
public class BoardApiController implements BoardApi {

    private final OrderRepository orderRepository;
    private final HttpServletRequest request;
    private final OfferRepository offerRepository;

    private static final String ACCEPT_HEADER = "Accept";
    private static final String ACCEPT_TYPE = "application/json";

    @Autowired
    public BoardApiController(OfferRepository offerRepository, OrderRepository orderRepository, HttpServletRequest request) {
        this.orderRepository = orderRepository;
        this.offerRepository = offerRepository;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<Offer>> getOffers() {
        List<Offer> offers = offerRepository.findAll();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> placeOrder(Order order) {
        String accept = request.getHeader(ACCEPT_HEADER);
        if (accept != null && accept.contains(ACCEPT_TYPE)) {
            try {
                orderRepository.store(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(order, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            return new ResponseEntity<>(order, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Order> getOrderById(Long orderId) {
        String accept = request.getHeader(ACCEPT_HEADER);
        if (accept != null && accept.contains(ACCEPT_TYPE)) {
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

    @Override
    public ResponseEntity<Order> updateOrderWithForm(Long orderId, Boolean completed, Boolean payed) {
        String accept = request.getHeader(ACCEPT_HEADER);
        if (accept != null && accept.contains(ACCEPT_TYPE)) {
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

    @Override
    public ResponseEntity<Void> deleteOrder(Long orderId) {
        orderRepository.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
