package dev.besharps.batesmotel.DB.Payment;

import dev.besharps.batesmotel.DB.Bookings.Bookings;
import dev.besharps.batesmotel.DB.User.User;
import dev.besharps.batesmotel.DB.User.UserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/payment/")
    Payment create(@Valid @RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/{id}")
    void updateDetails(@PathVariable Integer id, @RequestBody @Valid PaymentDetails myDetails) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Payment ID not found");
        }
        paymentService.updatePayment(payment, myDetails);
    }

}
