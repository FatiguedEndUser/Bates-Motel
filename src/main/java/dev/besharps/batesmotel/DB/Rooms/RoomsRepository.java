package dev.besharps.batesmotel.DB.Rooms;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomsRepository {

    //Database pulls and or related logic will go here.
    List<Rooms> findAll() {
        return new ArrayList<>();
    }
}
