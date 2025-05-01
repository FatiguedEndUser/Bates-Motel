package dev.besharps.batesmotel.DB.Payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.besharps.batesmotel.DB.Customer.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Payments")
@Table(name = "Payment")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, updatable = false)
    private int id;

    @Column(name = "cardnumber", nullable = false)
    private String cardNumber;

    @Column(name = "cardholder", nullable = false)
    private String name;

    @Column(name = "exp", nullable = false)
    private String exp;

    @Column(name = "cvv", nullable = false)
    private int cvv;

    @Column(name = "zip", nullable = false)
    private int zip;

    @JsonIgnore
    @ManyToMany(mappedBy = "payments")
    List<Customer> customers = new ArrayList<>();

    public void detachFromCustomer() {
        for (Customer customer : new ArrayList<>(customers)) {
            customer.getPayments().remove(this);
            this.customers.remove(customer);
        }
    }

    public Payments(String name, String cardNumber, String exp, int cvv, int zip) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.exp = exp;
        this.cvv = cvv;
        this.zip = zip;
    }


}
