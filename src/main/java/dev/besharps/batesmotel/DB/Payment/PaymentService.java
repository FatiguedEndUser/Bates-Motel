package dev.besharps.batesmotel.DB.Payment;

import dev.besharps.batesmotel.DB.User.User;
import dev.besharps.batesmotel.DB.User.UserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void updatePayment(Payment payment, PaymentDetails myPayment) {

        if (myPayment.cardholder() != null) {
            payment.setName(myPayment.cardholder());
        }
        if (myPayment.cvv() != null) {
            payment.setCvv(myPayment.cvv());
        }
        if (myPayment.expiration() != null) {
            payment.setDate(myPayment.expiration());
        }
        if (myPayment.zip() != null) {
            payment.setZip(myPayment.zip());
        }

        paymentRepository.save(payment);

    }
}
