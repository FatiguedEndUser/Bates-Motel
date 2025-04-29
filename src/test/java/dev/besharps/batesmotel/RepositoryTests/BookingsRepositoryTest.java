package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.BatesMotelApplication;
import dev.besharps.batesmotel.DB.Bookings.Bookings;
import dev.besharps.batesmotel.DB.Bookings.BookingsRepository;
import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Rooms.RoomsRepository;
import dev.besharps.batesmotel.DB.User.User;
import dev.besharps.batesmotel.DB.User.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatesMotelApplication.class)
public class BookingsRepositoryTest {

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(false)
    @Transactional
    public void createBooking() {
        Rooms selectedRoom = roomsRepository.findById(11).orElseThrow();
        Customer customer = customerRepository.findById(7).orElseThrow();
        LocalDate startDate = LocalDate.of(2025, 3, 27);
        LocalDate endDate = LocalDate.of(2025, 4, 22);
        Bookings booking = Bookings.builder()
                .customer(customer)
                .room(selectedRoom)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        bookingsRepository.save(booking);
    }

    @Test
    public void createBookingWithCustomer() {
        Rooms selectedRoom = roomsRepository.findById(10).orElseThrow();
        Customer customer = customerRepository.findById(1).orElseThrow();
        User selectedUser = userRepository.findById(1).orElseThrow();
        LocalDate startDate = LocalDate.of(2025, 3, 27);
        LocalDate endDate = LocalDate.of(2025, 4, 22);
        Bookings booking = Bookings.builder()
                .customer(customer)
                .room(selectedRoom)
                .startDate(startDate)
                .endDate(endDate)
                .user(selectedUser)
                .endDate(endDate)
                .build();
        bookingsRepository.save(booking);
    }
}
