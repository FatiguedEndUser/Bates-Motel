package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Services.Services;
import dev.besharps.batesmotel.DB.User.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity(name = "Bookings")
@Table(name = "Bookings")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Bookings {
    //Created for bookings page
    public Bookings(Customer customer, String roomType, LocalDate checkin, LocalDate checkout, Rooms room) {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId", nullable = false, updatable = false)
    private int bookingId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId",
            foreignKey = @ForeignKey(name = "booking_customer_fk"),
            nullable = false
    )
    private Customer customer;

    @ManyToOne
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roomNumber",
            foreignKey = @ForeignKey(name = "booking_room_fk")
    )
    private Rooms room;

    // mapping roomType into a column
    @Column(name = "room_type", nullable = false)
    private String roomType;

    // room_title column:
    @Column(name = "room_title", nullable = false)
    private String roomTitle;

    @ManyToMany
    @JoinTable(
            name = "booking_services",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Services> services;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "guests", nullable = false)
    private int guests;

    @Column(name = "room_preference", nullable = true)
    private String roomPreference;

    @Column(name = "floor_preference", nullable = true)
    private String floorPreference;

    public Bookings(Customer customer,
                    LocalDate startDate,
                    LocalDate endDate,
                    Rooms room) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room     = room;
    }
}