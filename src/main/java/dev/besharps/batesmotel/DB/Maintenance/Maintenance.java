package dev.besharps.batesmotel.DB.Maintenance;

import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Staff.Staff;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity(name = "Maintenance")
@Table(name = "Maintenance")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Maintenance {
    @Id
    @SequenceGenerator(
            name = "maintenance_sequence",
            sequenceName = "maintenance_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "maintenance_sequence"
    )
    @Column(
            name = "maintenanceId",
            updatable = false
    )
    private int maintenanceId;

    @ManyToOne
    @JoinColumn(
            name = "roomId",
            referencedColumnName = "roomId",
            foreignKey = @ForeignKey(name = "maintenance_room_fk"),
            nullable = false
    )
    private Rooms room;

    @ManyToOne
    @JoinColumn(
            name = "staffId",
            referencedColumnName = "staffId",
            foreignKey = @ForeignKey(name = "maintenance_staff_fk"),
            nullable = false
    )
    private Staff staff;

    @Column(
            name = "description",
            updatable = true,
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "requestDate",
            updatable = false,
            nullable = false
    )
    private LocalDate requestDate;

    @Column(
            name = "finishedDate",
            updatable = true,
            nullable = true
    )
    private LocalDate finishedDate;
}