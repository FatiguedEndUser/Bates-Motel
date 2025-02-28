package dev.besharps.batesmotel.DB;

import jakarta.persistence.*;

@Entity(name = "Rooms")
@Table(name = "Rooms")
public class Rooms {
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

    //----------------------------CONSTRUCTORS------------------------------------------------------//
    public Rooms() {

    }

    Rooms(int roomId, String roomType, int floor, int roomNumber) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.floor = floor;
        this.roomNumber = roomNumber;
    }
    //----------------------------CONSTRUCTORS------------------------------------------------------//


    //---------------------------------GETTERS/SETTERS---------------------------------------------//
    //Getters and setters for each respective field
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    //---------------------------------GETTERS/SETTERS---------------------------------------------//

    @Override
    public String toString() {
        return "Rooms{" +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                ", floor=" + floor +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
