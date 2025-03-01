package dev.besharps.batesmotel.DB.Rooms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
    //Crud operations come from JpaRepository class but we can specify specific grabs

}
