package dev.besharps.batesmotel.DB.Rooms;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Rooms")
@Table(name = "Rooms")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Rooms {
    @Id
    @SequenceGenerator(
            name = "room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_sequence"
    )
    @Column(
            name = "roomId",
            updatable = false
    )
    private Long roomId;

    @Column(
            name = "roomName",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String roomName;

    @Column(
            name = "roomType",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String roomType;

    @Column(
            name = "floor",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int floor;

    @Column(
            name = "roomNumber",
            nullable = false,
            unique = true,
            columnDefinition = "INTEGER"
    )
    private Long roomNumber;

    @Column(
            name = "available",
            nullable = false
    )
    private boolean available;

    @Column(
            name = "pricePerNight",
            nullable = false
    )
    private Double pricePerNight;

    @Column(
            name = "bedConfiguration",
            columnDefinition = "TEXT"
    )
    private String bedConfiguration;

    @Column(
            name = "appliances",
            columnDefinition = "TEXT"
    )
    private String appliances;

    @Column(
            name = "imageUrl",
            columnDefinition = "TEXT"
    )
    private String imageUrl;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;
}