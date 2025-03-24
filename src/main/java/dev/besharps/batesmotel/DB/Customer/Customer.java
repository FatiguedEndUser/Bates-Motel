package dev.besharps.batesmotel.DB.Customer;

import dev.besharps.batesmotel.DB.UserType.UserType;
import jakarta.persistence.*;
import lombok.*;

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
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    @Column(
            name = "customerId",
            nullable = false,
            updatable = false
    )
    private int customerId;

    @Column(
            name = "firstName",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "lastName",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "phoneNumber",
            updatable = true,
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String phoneNumber;

    @Column(
            name = "address",
            updatable = true,
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String address;

    @Column(
            name = "carInformation",
            updatable = true,
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String carInformation;

//    @ManyToOne
//    @JoinColumn(
//            name = "userTypeId",
//            referencedColumnName = "userTypeId",
//            foreignKey = @ForeignKey(name = "customer_usertype_fk"),
//            nullable = false
//    )
//
//    // Needs fixing
//    private UserType userType;
}