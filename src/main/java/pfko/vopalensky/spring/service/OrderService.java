package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.AuthenticationException;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.error.exception.NotFoundException;
import pfko.vopalensky.spring.model.Order;
import pfko.vopalensky.spring.repository.OrderRepository;
import pfko.vopalensky.spring.response.OfferResponse;
import pfko.vopalensky.spring.response.OrderResponse;
import pfko.vopalensky.spring.response.UserResponse;

import java.util.List;
import java.util.Objects;

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
     * @param order Order to be saved
     * @return Response object with new order
     */
    public ResponseEntity<OrderResponse> placeOrder(Order order) {
        try {
            orderRepository.store(order);
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
        try {
            Order found = orderRepository.get(orderId);
            if (found != null) {
                return ResponseEntity.ok(getOrderResponse(found));
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
    public ResponseEntity<OrderResponse> updateOrderWithForm(
            Long orderId, Boolean completed, Boolean payed) {
        try {
            Order order = orderRepository.get(orderId);
            if (order == null) {
                throw new NotFoundException(SCOPE);
            }

            if (userService.isCurrentlyLoggedIn(order.getCustomerId())) {
                order.setCompleted(completed);
            } else {
                throw new AuthenticationException("NOT_HIS_ORDER");
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
        orderRepository.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public OrderResponse getOrderResponse(Order order) {
        OfferResponse offerResponse =
                offerService.getOfferResponse(order.getOfferId());

        UserResponse userResponse =
                userService.getUserResponse(order.getCustomerId());

        return new OrderResponse(order.getId(), offerResponse, userResponse,
                order.isCompleted(), order.isPayed());
    }

    public OrderResponse getOrderResponse(Long orderId) {
        return getOrderResponse(orderRepository.get(orderId));
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


