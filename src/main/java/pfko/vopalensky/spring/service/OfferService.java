package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.error.exception.NotFoundException;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.repository.OfferRepository;
import pfko.vopalensky.spring.repository.ServiceRepository;
import pfko.vopalensky.spring.response.OfferResponse;
import pfko.vopalensky.spring.response.ServiceResponse;

import java.util.List;

@Service
public class OfferService {
    private static final String SCOPE = "Offer";

    private final OfferRepository offerRepository;
    private final ServiceRepository serviceRepository;
    private final TeamService teamService;
    private final UserService userService;
    private final MyServiceService myServiceService;

    @Autowired
    public OfferService(OfferRepository offerRepository,
                        ServiceRepository serviceRepository,
                        TeamService teamService,
                        UserService userService,
                        MyServiceService myServiceService) {
        this.offerRepository = offerRepository;
        this.serviceRepository = serviceRepository;
        this.teamService = teamService;
        this.userService = userService;
        this.myServiceService = myServiceService;
    }

    /**
     * Get all offers from db
     *
     * @return all stored offers
     */
    public ResponseEntity<List<OfferResponse>> getOffers() {
        return ResponseEntity.ok(getAllResponses());
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
            return ResponseEntity.ok(getOfferResponse(offer));
        } catch (Exception e) {
            throw new FieldValidationException(SCOPE);
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
                throw new NotFoundException(SCOPE);
            }
            toChange.setName(offer.getName());
            toChange.setCost(offer.getCost());
            //toChange.setServicesIds(offer.getServicesIds());
            toChange.setCreatorId(offer.getCreatorId());
            return ResponseEntity.ok(getOfferResponse(toChange));
        } catch (Exception e) {
            throw new FieldValidationException(SCOPE);
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
            throw new NotFoundException(SCOPE);
        }
        return ResponseEntity.ok(getOfferResponse(found));
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
            Long offerId, String name, Long cost, List<Long> services, Long created) {
        try {
            Offer toChange = offerRepository.get(offerId);
            if (toChange == null) {
                throw new NotFoundException(SCOPE);
            }
            if (name != null) {
                toChange.setName(name);
            }
            if (cost != null) {
                toChange.setCost(cost);
            }
            if (services != null) {
                //toChange.setServicesIds(services);
            }
            if (created != null) {
                toChange.setCreatorId(created);
            }
            return ResponseEntity.ok(getOfferResponse(toChange));
        } catch (Exception e) {
            throw new FieldValidationException(SCOPE);
        }
    }

    /**
     * Create offer response from database offer object
     *
     * @param offer DB offer entity
     * @return Offer response entity
     */
    public OfferResponse getOfferResponse(Offer offer) {
        List<ServiceResponse> services =
                offer.getServices().stream()
                        .map(myServiceService::getServiceResponse)
                        .toList();

        /*
        CreatorResponse creatorResponse;
        if (creator.getCreatorType() == CreatorType.INDIVIDUAL) {
            creatorResponse = userService.getUserResponse((User) creator);
        } else {
            creatorResponse = teamService.getTeamResponse((SupplierTeam) creator);
        }

         */

        return new OfferResponse(
                offer.getId(), offer.getName(), offer.getCost(),
                services, null);
    }

    /**
     * Create offer response from database offer object
     *
     * @param offerId ID of database offer object
     * @return Offer response entity
     */
    public OfferResponse getOfferResponse(Long offerId) {
        return getOfferResponse(offerRepository.get(offerId));
    }

    /**
     * Get all offers from database and return them as response offer objects
     *
     * @return List of offer response objects
     */
    public List<OfferResponse> getAllResponses() {
        return offerRepository.findAll().stream()
                .map(this::getOfferResponse)
                .toList();
    }
}
