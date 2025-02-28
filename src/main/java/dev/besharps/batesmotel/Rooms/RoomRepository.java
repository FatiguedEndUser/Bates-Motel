package dev.besharps.batesmotel.Rooms;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {

    //Database pulls and or related logic will go here.
    List<Room> findAll() {
        return new ArrayList<>();
    }
}
