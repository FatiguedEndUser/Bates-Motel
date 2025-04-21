package dev.besharps.batesmotel.DB.Customer;

import dev.besharps.batesmotel.DB.Payment.Payments;
import dev.besharps.batesmotel.DB.Services.Services;
import dev.besharps.batesmotel.DB.UserType.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity(name = "Customer")
@Table(name = "Customer")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Customer {

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false, updatable = false)
    private int customerId;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "carInformation")
    private String carInformation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userTypeId")
    private UserType userType;

    @ManyToMany
    @JoinTable(
            name = "customer_payments",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id")
    )
    private List<Payments> payments;

    public void addPayment(Payments payment) {
        if (this.payments.contains(payment)) {
            throw new IllegalArgumentException("Duplicate payment detected");
        }
        this.payments.add(payment);
    }
}