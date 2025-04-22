package dev.besharps.batesmotel.DB.Services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/services")
public class ServicesController {

    private final ServicesRepository servicesRepository;

    public ServicesController(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        Services service = servicesRepository.findById(id).orElse(null);
        if (service == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Service ID not found");
        }
        service.detachFromBooking();
        servicesRepository.delete(service);
    }
}
