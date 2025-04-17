package dev.besharps.batesmotel.DB.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.besharps.batesmotel.DB.Bookings.Bookings;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId", updatable = false)
    private Integer serviceId;

    @JsonIgnore
    @ManyToMany(mappedBy = "services")
    List<Bookings> bookings = new ArrayList<>();

    @Column(name = "serviceName", nullable = false)
    private String serviceName;

    @Column(name = "serviceCategory")
    private String serviceCategory;

    @Column(name = "serviceCharge", nullable = false)
    private double serviceCharge;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "available")
    private boolean available = true;

    public void detachFromBooking() {
        for (Bookings booking : new ArrayList<>(bookings)) {
            booking.getServices().remove(this);
            this.bookings.remove(booking);
        }
    }
}