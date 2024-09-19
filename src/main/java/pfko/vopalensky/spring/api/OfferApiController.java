package pfko.vopalensky.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Service;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.OfferRepository;
import pfko.vopalensky.spring.response.OfferResponse;
import pfko.vopalensky.spring.service.Helper;

import java.util.List;

@RestController
public class OfferApiController {

    private final OfferRepository offerRepository;


    @Autowired
    public OfferApiController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    /**
     * Returns a list of all offers
     *
     * @return List of all offers
     */
    @GetMapping(value = "/offer", produces = "application/json")
    public ResponseEntity<List<OfferResponse>> getOffers() {
        List<OfferResponse> offers = offerRepository.getResponses();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    /**
     * Create new offer
     *
     * @param offer New offer
     * @return Newly created offer
     */
    @PostMapping(value = "/offer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferResponse> addOffer(@RequestBody Offer offer) {
        try {
            offerRepository.store(offer);
            return new ResponseEntity<>(new OfferResponse(offer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    /**
     * Update an existing offer by ID
     *
     * @param offer Updated existing offer on board
     * @return Newly updated offer
     */
    @PutMapping(value = "/offer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferResponse> updateOffer(@RequestBody Offer offer) {
        try {
            Offer toChange = offerRepository.get(offer.getId());
            if (toChange == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            toChange.setName(offer.getName());
            toChange.setCost(offer.getCost());
            toChange.setServices(offer.getServices());
            toChange.setCreatedBy(offer.getCreatedBy());
            return new ResponseEntity<>(new OfferResponse(toChange), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Deletes an offer by id
     *
     * @param offerId Offer of id to delete
     */
    @DeleteMapping(value = "/offer/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable(name = "offerId") Long offerId) {
        offerRepository.delete(offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Find an offer by ID
     *
     * @param offerId ID of an offer to return
     * @return Found offer
     */
    @GetMapping(value = "/offer/{offerId}", produces = "application/json")
    public ResponseEntity<OfferResponse> getOfferById(@PathVariable(name = "offerId") Long offerId) {
        try {
            Offer found = offerRepository.get(offerId);
            if (found != null) {
                return new ResponseEntity<>(new OfferResponse(found), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    @PostMapping(value = "/offer/{offerId}", produces = "application/json")
    public ResponseEntity<OfferResponse> updateOfferWithForm(
            @PathVariable(name = "offerId") Long offerId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cost", required = false) Long cost,
            @RequestParam(value = "services", required = false) List<Service> services,
            @RequestParam(value = "created", required = false) User created) {
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
            return new ResponseEntity<>(new OfferResponse(toChange), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
