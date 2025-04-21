package dev.besharps.batesmotel.DB.Payment;

import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerRepository customerRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/payment/{id}")
    Payments create(@PathVariable Integer id, @RequestBody @Valid Payments payment) {
        return paymentService.createPayment(payment, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/{id}")
    void updateDetails(@PathVariable Integer id, @RequestBody @Valid PaymentDetails myDetails) {
        Payments payment = paymentRepository.findById(id).orElse(null);
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Payment ID not found");
        }
        paymentService.updatePayment(payment, myDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        Payments payment = paymentRepository.findById(id).orElse(null);
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Payment ID not found");
        }
        //BREAKS THE PROGRAM
        //payment.detachFromCustomer();
        paymentRepository.delete(payment);
    }
}
