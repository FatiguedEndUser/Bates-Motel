package dev.besharps.batesmotel.DB.Rooms;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomsService {
    @Autowired
    private RoomsRepository roomsRepository;

    @Transactional
    public void updateRoom(Long roomId, String roomType, Boolean available) {
        Rooms room = roomsRepository.findById(roomId.intValue()).orElse(null);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }

        // Update only fields that are meant to be updatable
        if (roomType != null) {
            room.setRoomType(roomType);
        }
        if (available != null) {
            room.setAvailable(available);
        }

        roomsRepository.save(room);
    }
}