package dev.besharps.batesmotel.DB.Services;

import dev.besharps.batesmotel.Exceptions.ServiceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {


    private final ServicesRepository servicesRepository;

    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public List<Services> getAllServices() {
        return servicesRepository.findAll();
    }

    public Services getServiceById(Integer id) {
        return servicesRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found"));
    }

    public List<Services> getServicesByCategory(String category) {
        return servicesRepository.findByServiceCategory(category);
    }

    public List<Services> getAvailableServices() {
        return servicesRepository.findByAvailable(true);
    }

    public List<Services> searchServices(String category, Double maxPrice, String keyword) {
        return servicesRepository.searchServices(category, maxPrice, keyword);
    }

    public Services saveService(Services service) {
        return servicesRepository.save(service);
    }

    public Services updateService(
            Integer id,
            String serviceName,
            String serviceCategory,
            double serviceCharge,
            String description,
            boolean available,
            String imageUrl) {

        Services service = servicesRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found"));

        service.setServiceName(serviceName);
        service.setServiceCategory(serviceCategory);
        service.setServiceCharge(serviceCharge);
        service.setDescription(description);
        service.setAvailable(available);

        if (imageUrl != null) {
            service.setImageUrl(imageUrl);
        }

        return servicesRepository.save(service);
    }

    public void deleteService(Integer id) {
        Services service = servicesRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found"));

        service.detachFromBooking();
        servicesRepository.delete(service);
    }
}