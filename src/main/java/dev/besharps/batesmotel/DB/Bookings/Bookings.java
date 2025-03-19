package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Services.Services;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity(name = "Bookings")
@Table(name = "Bookings")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Bookings {
    @Id
    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_sequence"
    )
    @Column(
            name = "bookingId",
            nullable = false,
            updatable = false
    )
    private int bookingId;

    @ManyToOne
    @JoinColumn(
            name = "customerId",
            referencedColumnName = "customerId",
            foreignKey = @ForeignKey(name = "booking_customer_fk"),
            nullable = false
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "roomNumber",
            referencedColumnName = "roomNumber",
            foreignKey = @ForeignKey(name = "booking_room_fk"),
            nullable = false
    )
    private Rooms room;

    @ManyToOne
    @JoinColumn(
            name = "serviceId",
            referencedColumnName = "serviceId",
            foreignKey = @ForeignKey(name = "booking_service_fk")
    )
    private Services service;

    @Column(
            name = "startDate",
            nullable = false
    )
    private LocalDate startDate;

    @Column(
            name = "endDate",
            nullable = false
    )
    private LocalDate endDate;
}