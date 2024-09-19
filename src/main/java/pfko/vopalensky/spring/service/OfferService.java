package pfko.vopalensky.spring.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.error.exception.NotFoundException;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.model.User;
import pfko.vopalensky.spring.repository.OfferRepository;
import pfko.vopalensky.spring.response.OfferResponse;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Get all offers from db
     *
     * @return all stored offers
     */
    public ResponseEntity<List<OfferResponse>> getOffers() {
        return new ResponseEntity<>(offerRepository.getResponses(), HttpStatus.OK);
    }

    /**
     * Add new offer to a database
     *
     * @param offer offer to be added
     * @return added offer
     */
    public ResponseEntity<OfferResponse> addOffer(Offer offer) {
        try {
            offerRepository.store(offer);
            return new ResponseEntity<>(new OfferResponse(offer), HttpStatus.OK);
        } catch (Exception e) {
            throw new FieldValidationException("Offer");
        }
    }

    /**
     * Update an existing offer in db
     *
     * @param offer to replace the old one with
     * @return new offer
     */
    public ResponseEntity<OfferResponse> updateOffer(Offer offer) {
        try {
            Offer toChange = offerRepository.get(offer.getId());
            if (toChange == null) {
                throw new NotFoundException("Offer");
            }
            toChange.setName(offer.getName());
            toChange.setCost(offer.getCost());
            toChange.setServices(offer.getServices());
            toChange.setCreatedBy(offer.getCreatedBy());
            return new ResponseEntity<>(new OfferResponse(toChange), HttpStatus.OK);
        } catch (Exception e) {
            throw new FieldValidationException("Offer");
        }
    }

    /**
     * Delete an offer from dv
     *
     * @param offerId ID of offer to delete
     * @return response ok
     */
    public ResponseEntity<Void> deleteOffer(Long offerId) {
        offerRepository.delete(offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Find offer by its id
     *
     * @param offerId ID to find offer by
     * @return found offer
     */
    public ResponseEntity<OfferResponse> getOfferById(Long offerId) {
        Offer found = offerRepository.get(offerId);
        if (found == null) {
            throw new NotFoundException("Offer");
        }
        return new ResponseEntity<>(new OfferResponse(found), HttpStatus.OK);
    }

    /**
     * Updates an offer in db
     *
     * @param offerId  ID of an offer that needs to be updated
     * @param name     Name of an offer that needs to be updated
     * @param cost     Cost of an offer that needs to be updated
     * @param services Services in an offer that needs to be updated
     * @param created  ID of worker/team that created this offer
     */
    public ResponseEntity<OfferResponse> updateOfferWithForm(
            Long offerId, String name, Long cost, List<pfko.vopalensky.spring.model.Service> services, User created) {
        try {
            Offer toChange = offerRepository.get(offerId);
            if (toChange == null) {
                throw new NotFoundException("Offer");
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
            throw new FieldValidationException("Offer");
        }
    }
}
