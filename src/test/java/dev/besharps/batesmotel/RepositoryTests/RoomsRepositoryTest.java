package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Rooms.RoomsRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RoomsRepositoryTest {

    @Autowired
    private RoomsRepository roomsRepository;

    Logger logger = Logger.getLogger(RoomsRepositoryTest.class.getName());

    @BeforeEach
    @Transactional
    @Rollback(false)
    void setUp() {
        Rooms room1 = new Rooms();
        room1.setRoomNumber(101L);
        room1.setFloor(2);
        room1.setRoomType("Single");
        room1.setAvailable(true);
        roomsRepository.save(room1);

        Rooms room2 = new Rooms();
        room2.setRoomNumber(102L);
        room2.setFloor(3);
        room2.setRoomType("Double");
        room2.setAvailable(true);
        roomsRepository.save(room2);
    }
    @Test
    void findByRoomType() {
        List<Rooms> Room = roomsRepository.findByRoomType("Single");
        for (Rooms listRoom : Room) {
            logger.info(listRoom.toString());
        }
    }

    @Test
    void findByRoomNumber() {
        List<Rooms> Room = roomsRepository.findByRoomNumber(101L);
        for (Rooms listRoom : Room) {
            logger.info(listRoom.toString());
        }
    }

    @Test
    void getAvaliableRooms() {
        List<Rooms> Room = roomsRepository.findByAvailableTrue();
        for (Rooms listRoom : Room) {
            logger.info(listRoom.toString());
        }
    }
}