package dev.besharps.batesmotel.DB.Payment;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity(name = "Payments")
@Table(name = "Payment")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, updatable = false)
    private int id;

    @Column(name = "cardholder", nullable = false)
    private String name;

    @Column(name = "exp", nullable = false)
    private LocalDate date;

    @Column(name = "cvv", nullable = false)
    private int cvv;

    @Column(name = "zip", nullable = false)
    private int zip;

}
