package dev.besharps.batesmotel.DB.Customer;

import dev.besharps.batesmotel.DB.UserType.UserType;
import jakarta.persistence.*;
import lombok.*;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId", nullable = false, updatable = false)
    private int customerId;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "carInformation")
    private String carInformation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userTypeId")
    private UserType userType;
}