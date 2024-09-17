package pfko.vopalensky.spring.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Service;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.OfferRepository;

import java.util.List;

@RestController
public class OfferApiController implements OfferApi {

    private final OfferRepository offerRepository;
    private final HttpServletRequest request;


    @Autowired
    public OfferApiController(OfferRepository offerRepository, HttpServletRequest request) {
        this.offerRepository = offerRepository;
        this.request = request;
    }

    /**
     * Create new offer
     *
     * @param offer New offer
     * @return Newly created offer
     */
    @Override
    public ResponseEntity<Offer> addOffer(Offer offer) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                offerRepository.store(offer);
                return new ResponseEntity<>(offer, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Update an existing offer by ID
     *
     * @param offer Updated existing offer on board
     * @return Newly updated offer
     */
    @Override
    public ResponseEntity<Offer> updateOffer(Offer offer) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Offer toChange = offerRepository.get(offer.getId());
                if (toChange == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                toChange.setName(offer.getName());
                toChange.setCost(offer.getCost());
                toChange.setServices(offer.getServices());
                toChange.setCreatedBy(offer.getCreatedBy());
                return new ResponseEntity<>(toChange, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes an offer by id
     *
     * @param offerId Offer of id to delete
     */
    @Override
    public ResponseEntity<Void> deleteOffer(Long offerId) {
        offerRepository.delete(offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Find an offer by ID
     *
     * @param offerId ID of an offer to return
     * @return Found offer
     */
    @Override
    public ResponseEntity<Offer> getOfferById(Long offerId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Offer found = offerRepository.get(offerId);
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
     * Updates an offer with form data
     *
     * @param offerId  ID of an offer that needs to be updated
     * @param name     Name of an offer that needs to be updated
     * @param cost     Cost of an offer that needs to be updated
     * @param services Services in an offer that needs to be updated
     * @param created  ID of worker/team that created this offer
     */
    @Override
    public ResponseEntity<Offer> updateOfferWithForm(Long offerId, String name, Long cost, List<Service> services, User created) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Offer toChange = offerRepository.get(offerId);
                if (toChange == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                if (name != null) {
                    toChange.setName(name);
                }
                if (cost != null) {
                    toChange.setCost(cost);
                }
                if (services != null) {
                    toChange.setServices(services);
                }
                if (created != null) {
                    toChange.setCreatedBy(created);
                }
                return new ResponseEntity<>(toChange, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return null;
    }
}
