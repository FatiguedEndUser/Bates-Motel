package dev.besharps.batesmotel.DB.Payment;

import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Payments createPayment(Payments payment, Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        customer.addPayment(payment);
        paymentRepository.save(payment);
        return payment;
    }

    @Transactional
    public void updatePayment(Payments payment, PaymentDetails myPayment) {
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
