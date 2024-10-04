package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.error.exception.NotFoundException;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.repository.OrderRepository;
import pfko.vopalensky.spring.request.OrderRequest;
import pfko.vopalensky.spring.response.OfferResponse;
import pfko.vopalensky.spring.response.OrderResponse;
import pfko.vopalensky.spring.response.UserResponse;

import java.util.List;

@Service
public class OrderService {

    private static final String SCOPE = "Order";

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OfferService offerService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserService userService,
                        OfferService offerService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.offerService = offerService;
    }

    /**
     * Get all orders
     *
     * @return All orders in db
     */
    public ResponseEntity<List<OrderResponse>> getOrders() {
        List<OrderResponse> orders = getAllResponses();
        return ResponseEntity.ok(orders);
    }

    /**
     * Save new order into db
     *
     * @param orderRequest Request for order object to be saved
     * @return Response object with new order
     */
    public ResponseEntity<OrderResponse> placeOrder(OrderRequest orderRequest) {
        Order order = new Order(
                offerService.getOfferById(orderRequest.getOfferId()),
                userService.getUserById(orderRequest.getUserId()),
                orderRequest.getCompleted(),
                orderRequest.getPayed()
        );
        try {
            orderRepository.save(order);
            return ResponseEntity.ok(getOrderResponse(order));
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

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(SCOPE));


        if (userService.isCurrentlyLoggedIn(order.getCustomer().getId()) ||
                userService.isThisSupplier()) {
            return ResponseEntity.ok(getOrderResponse(order));
        } else {
            throw new AccessDeniedException("NOT_HIS_ORDER");
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
    public ResponseEntity<OrderResponse> updateOrderWithForm(
            Long orderId, Boolean completed, Boolean payed) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException(SCOPE));

            if (userService.isCurrentlyLoggedIn(order.getCustomer().getId())) {
                order.setCompleted(completed);
            } else {
                throw new AccessDeniedException("NOT_HIS_ORDER");
            }

            if (userService.isThisSupplier()) {
                order.setPayed(payed);
            }

            return ResponseEntity.ok(getOrderResponse(order));
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
        orderRepository.deleteById(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public OrderResponse getOrderResponse(Order order) {
        OfferResponse offerResponse =
                offerService.getOfferResponse(order.getOffer());

        UserResponse userResponse =
                userService.getUserResponse(order.getCustomer());

        return new OrderResponse(order.getId(), offerResponse, userResponse,
                order.isCompleted(), order.isPayed());
    }

    public OrderResponse getOrderResponse(Long orderId) {
        return getOrderResponse(orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(SCOPE)));
    }

    public List<OrderResponse> getAllResponses() {
        return orderRepository.findAll().stream()
                .map(this::getOrderResponse)
                .toList();
    }


    /**
     * Return all orders of currently logged-in user
     *
     * @return List of orders of current user
     */
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        List<OrderResponse> responses = getAllResponses().stream()
                .filter(or -> userService.isCurrentlyLoggedIn(or.getCustomer().getUserName()))
                .toList();

        return ResponseEntity.ok(responses);
    }
}


