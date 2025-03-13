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
            updatable = false
    )
    private int customerId;

    @Column(
            name = "name",
            updatable = true,
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "number",
            updatable = true,
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String number;

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

    @ManyToOne
    @JoinColumn(
            name = "typeId",
            referencedColumnName = "userTypeId",
            foreignKey = @ForeignKey(name = "customer_usertype_fk"),
            nullable = false
    )

    // Needs fixing
    private UserType userType;
}