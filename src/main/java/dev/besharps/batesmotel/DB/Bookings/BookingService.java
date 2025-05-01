package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.DB.Services.Services;
import dev.besharps.batesmotel.DB.Services.ServicesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final BookingsRepository bookingsRepository;
    private final ServicesRepository servicesRepository;

    public BookingService(BookingsRepository bookingsRepository, ServicesRepository servicesRepository) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
    }

    @Transactional
    public Bookings addService(Bookings booking, int serviceType) {
        ServiceDetails details = switch (serviceType) {
            case 1 -> new ServiceDetails("Cleaning", 6.99);
            case 2 -> new ServiceDetails("Valet", 15.99);
            default -> throw new IllegalStateException("Unexpected number: " + serviceType);
        };

        Services newService = Services.builder()
                .serviceCharge(details.charge())
                .serviceName(details.service())
                .build();
        servicesRepository.save(newService);
        booking.getServices().add(newService);
        return bookingsRepository.save(booking);
    }
}
