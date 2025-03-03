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
    //---------------------------------GETTERS/SETTERS---------------------------------------------//
    //Getters and setters for each respective field
    //This (roomId) is our primary key (PK)
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
            name = "id",
            updatable = false
    )
    private int roomId;

    @Column(
            name = "roomType",
            updatable = true,
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String roomType;

    @Column(
            name = "floor",
            updatable = false,
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int floor;

    @Column(
            name = "roomNumber",
            updatable = false,
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int roomNumber;
}
