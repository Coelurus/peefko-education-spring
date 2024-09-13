package pfko.vopalensky.spring.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.Service;
import pfko.vopalensky.spring.model.User;

import java.util.List;

@RestController
public interface OfferApi {

    /**
     * Create new offer
     *
     * @param offer New offer
     * @return Newly created offer
     */
    @PostMapping(value = "/offer",
            produces = {"application/json", "application/xml"},
            consumes = {"application/json", "application/xml"})
    ResponseEntity<Offer> addOffer(@RequestBody Offer offer);

    /**
     * Update an existing offer by ID
     *
     * @param offer Updated existing offer on board
     * @return Newly updated offer
     */
    @PutMapping(value = "/offer",
            produces = {"application/json", "application/xml"},
            consumes = {"application/json", "application/xml"})
    ResponseEntity<Offer> updateOffer(@RequestBody Offer offer);

    /**
     * Deletes an offer by id
     *
     * @param offerId Offer of id to delete
     */
    @DeleteMapping(value = "/offer/{offerId}")
    ResponseEntity<Void> deleteOffer(@PathVariable("offerId") Long offerId);

    /**
     * Find an offer by ID
     *
     * @param offerId ID of an offer to return
     * @return Found offer
     */
    @GetMapping(value = "/offer/{offerId}",
            produces = {"application/json", "application/xml"})
    ResponseEntity<Offer> getOfferById(@PathVariable("offerId") Long offerId);

    /**
     * Updates an offer with form data
     *
     * @param offerId  ID of an offer that needs to be updated
     * @param name     Name of an offer that needs to be updated
     * @param cost     Cost of an offer that needs to be updated
     * @param services Services in an offer that needs to be updated
     * @param created  ID of worker/team that created this offer
     */
    @PostMapping(value = "/offer/{offerId}")
    ResponseEntity<Void> updateOfferWithForm(
            @PathVariable("offerId") Long offerId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cost", required = false) Long cost,
            @RequestParam(value = "services", required = false) List<Service> services,
            @RequestParam(value = "created", required = false) User created);
}
