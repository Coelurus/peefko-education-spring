package pfko.vopalensky.spring.service;

import org.springframework.stereotype.Service;
import pfko.vopalensky.spring.model.MyService;
import pfko.vopalensky.spring.response.ServiceResponse;

@Service
public class MyServiceService {

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
}
