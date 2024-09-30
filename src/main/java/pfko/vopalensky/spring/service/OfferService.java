package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.error.exception.FieldValidationException;
import pfko.vopalensky.spring.error.exception.NotFoundException;
import pfko.vopalensky.spring.model.CreatorType;
import pfko.vopalensky.spring.model.MyService;
import pfko.vopalensky.spring.model.Offer;
import pfko.vopalensky.spring.repository.OfferRepository;
import pfko.vopalensky.spring.request.OfferRequest;
import pfko.vopalensky.spring.response.CreatorResponse;
import pfko.vopalensky.spring.response.OfferResponse;
import pfko.vopalensky.spring.response.ServiceResponse;

import java.util.List;

@Service
public class OfferService {
    private static final String SCOPE = "Offer";

    private final OfferRepository offerRepository;
    private final TeamService teamService;
    private final UserService userService;
    private final MyServiceService myServiceService;

    @Autowired
    public OfferService(OfferRepository offerRepository,
                        TeamService teamService,
                        UserService userService,
                        MyServiceService myServiceService) {
        this.offerRepository = offerRepository;
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
     * @return added offer
     */
    public ResponseEntity<OfferResponse> addOffer(OfferRequest offerRequest) {

        List<MyService> services =
                myServiceService.getServicesByIds(offerRequest.getServicesIds());
        Offer newOffer = new Offer(
                offerRequest.getName(),
                offerRequest.getCost(),
                services,
                offerRequest.getCreatorType(),
                offerRequest.getCreatorId());
        try {
            offerRepository.save(newOffer);
            return ResponseEntity.ok(getOfferResponse(newOffer));
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
            Offer toChange = offerRepository.findById(offer.getId())
                    .orElseThrow(() -> new NotFoundException(SCOPE));

            toChange.setName(offer.getName());
            toChange.setCost(offer.getCost());
            toChange.setServices(offer.getServices());
            toChange.setCreatorId(offer.getCreatorId());
            offerRepository.save(toChange);
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
        offerRepository.deleteById(offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Find offer by its id
     *
     * @param offerId ID to find offer by
     * @return found offer
     */
    public ResponseEntity<OfferResponse> getOfferResponseById(Long offerId) {
        Offer found = offerRepository.findById(offerId)
                .orElseThrow(() -> new NotFoundException(SCOPE));
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
            Long offerId, String name, Long cost,
            List<MyService> services, Long created) {
        try {
            Offer toChange = offerRepository.findById(offerId)
                    .orElseThrow(() -> new NotFoundException(SCOPE));
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

        CreatorResponse creatorResponse;
        if (offer.getCreatorType() == CreatorType.INDIVIDUAL) {
            creatorResponse = userService.getUserResponse(offer.getCreatorId());
        } else {
            creatorResponse = teamService.getTeamResponse(offer.getCreatorId());
        }


        return new OfferResponse(
                offer.getId(), offer.getName(), offer.getCost(),
                services, creatorResponse);
    }

    /**
     * Create offer response from database offer object
     *
     * @param offerId ID of database offer object
     * @return Offer response entity
     */
    public OfferResponse getOfferResponse(Long offerId) {
        return getOfferResponse(offerRepository.findById(offerId)
                .orElseThrow(() -> new NotFoundException(SCOPE)));
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

    public Offer getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SCOPE));
    }
}
