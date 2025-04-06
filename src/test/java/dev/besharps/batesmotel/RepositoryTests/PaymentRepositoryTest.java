package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.BatesMotelApplication;
import dev.besharps.batesmotel.DB.Payment.Payment;
import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
import dev.besharps.batesmotel.DB.UserType.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatesMotelApplication.class)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void createPayment() {
        LocalDate expDate = LocalDate.of(2027, 1, 18);
        Payment payment = Payment.builder()
                .name("Visa")
                .date(expDate)
                .cvv(501)
                .zip(55021)
                .build();
        paymentRepository.save(payment);
    }
}
