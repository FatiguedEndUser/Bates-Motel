package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.BatesMotelApplication;
import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import dev.besharps.batesmotel.DB.Payment.PaymentService;
import dev.besharps.batesmotel.DB.Payment.Payments;
import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
import dev.besharps.batesmotel.Exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatesMotelApplication.class)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentService paymentService;

    @Test
    public void createPayment() {
        LocalDate expDate = LocalDate.of(2027, 1, 18);
        Payments payment = Payments.builder()
                .name("Visa")
                .date(expDate)
                .cvv(501)
                .zip(55021)
                .build();
        paymentRepository.save(payment);
    }

    @Test
    public void createPaymentWithExistingCustomer() {
        LocalDate expDate = LocalDate.of(2028, 4, 21);
        Payments payment = Payments.builder()
                .name("Discover")
                .date(expDate)
                .cvv(322)
                .zip(55023)
                .build();
        paymentService.createPayment(payment, 1);
    }
}
