package dev.besharps.batesmotel.DB.Services;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Services")
@Table(name = "Services")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Services {
    @Id
    @SequenceGenerator(
            name = "service_sequence",
            sequenceName = "service_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_sequence"
    )
    @Column(
            name = "serviceId",
            updatable = false
    )
    private int serviceId;

    @Column(
            name = "serviceCharge",
            updatable = true,
            nullable = false
    )
    private double serviceCharge;
}