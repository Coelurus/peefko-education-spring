package pfko.vopalensky.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.model.MyService;
import pfko.vopalensky.spring.repository.ServiceRepository;
import pfko.vopalensky.spring.response.ServiceResponse;

import java.util.List;

@Service
public class MyServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public MyServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Create service response from database service object
     *
     * @param myService DB service entity
     * @return Service response entity
     */
    public ServiceResponse getServiceResponse(MyService myService) {
        return new ServiceResponse(myService.getId(),
                myService.getName(),
                myService.getDescription());
    }

    /**
     * Return all services from db corresponding to any given id
     *
     * @param ids List of ids to find services for
     * @return List of services from database
     */
    public List<MyService> getServicesByIds(List<Long> ids) {
        return serviceRepository.findAllById(ids);
    }
}
