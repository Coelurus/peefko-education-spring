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
import pfko.vopalensky.spring.service.OfferService;

import java.util.List;

@RestController
public class OfferApiController {

    private final OfferService offerService;
    
    @Autowired
    public OfferApiController(OfferRepository offerRepository, OfferService offerService) {
        this.offerService = offerService;
    }


    /**
     * Returns a list of all offers
     *
     * @return List of all offers
     */
    @GetMapping(value = "/offer", produces = "application/json")
    public ResponseEntity<List<OfferResponse>> getOffers() {
        return offerService.getOffers();
    }

    /**
     * Create new offer
     *
     * @param offer New offer
     * @return Newly created offer
     */
    @PostMapping(value = "/offer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferResponse> addOffer(@RequestBody Offer offer) {
        return offerService.addOffer(offer);
    }


    /**
     * Update an existing offer by ID
     *
     * @param offer Updated existing offer on board
     * @return Newly updated offer
     */
    @PutMapping(value = "/offer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferResponse> updateOffer(@RequestBody Offer offer) {
        return offerService.updateOffer(offer);
    }

    /**
     * Deletes an offer by id
     *
     * @param offerId Offer of id to delete
     */
    @DeleteMapping(value = "/offer/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable(name = "offerId") Long offerId) {
        return offerService.deleteOffer(offerId);
    }

    /**
     * Find an offer by ID
     *
     * @param offerId ID of an offer to return
     * @return Found offer
     */
    @GetMapping(value = "/offer/{offerId}", produces = "application/json")
    public ResponseEntity<OfferResponse> getOfferById(@PathVariable(name = "offerId") Long offerId) {
        return offerService.getOfferById(offerId);
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
        return offerService.updateOfferWithForm(offerId, name, cost, services, created);
    }
}
