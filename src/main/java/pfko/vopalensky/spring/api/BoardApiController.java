package pfko.vopalensky.spring.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        return null;
    }

    @Override
    public ResponseEntity<Order> getOrderById(Long orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateOrderWithForm(Long orderId, Boolean completed, Boolean payed) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOrder(Long orderId) {
        return null;
    }
}
