package dev.besharps.batesmotel.DB.Rooms;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    private final RoomsRepository roomsRepository;

    public RoomsService(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    public List<Rooms> getAllRooms() {
        return roomsRepository.findAll();
    }

    public List<Rooms> getRoomsByType(String roomType) {
        return roomsRepository.findByRoomType(roomType);
    }

    public List<Rooms> getAvailableRooms() {
        return roomsRepository.findByAvailableTrue();
    }

    public List<Rooms> getAvailableRoomsByType(String roomType) {
        return roomsRepository.findByRoomTypeAndAvailableTrue(roomType);
    }

    public List<Rooms> getRoomsByPriceRange(Double minPrice, Double maxPrice) {
        return roomsRepository.findByPricePerNightBetween(minPrice, maxPrice);
    }

    public List<Rooms> searchRooms(String roomType, Double maxPrice, String bedType) {
        if (roomType != null && maxPrice != null) {
            return roomsRepository.findAvailableRoomsByTypeAndMaxPrice(roomType, maxPrice);
        } else if (roomType != null) {
            return roomsRepository.findByRoomTypeAndAvailableTrue(roomType);
        } else if (maxPrice != null) {
            return roomsRepository.findByPricePerNightLessThanEqual(maxPrice);
        } else if (bedType != null) {
            return roomsRepository.findByBedConfigurationContaining(bedType);
        }
        return roomsRepository.findByAvailableTrue();
    }

    @Transactional
    public void saveRoom(Rooms room) {
        roomsRepository.save(room);
    }

    @Transactional
    public void updateRoom(Long roomId, String roomName, String roomType, Double pricePerNight,
                           String bedConfiguration, String appliances, Boolean available,
                           String imageUrl, String description) {
        Rooms room = roomsRepository.findById(roomId.intValue()).orElse(null);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }

        if (roomName != null) room.setRoomName(roomName);
        if (roomType != null) room.setRoomType(roomType);
        if (pricePerNight != null) room.setPricePerNight(pricePerNight);
        if (bedConfiguration != null) room.setBedConfiguration(bedConfiguration);
        if (appliances != null) room.setAppliances(appliances);
        if (available != null) room.setAvailable(available);
        if (imageUrl != null) room.setImageUrl(imageUrl);
        if (description != null) room.setDescription(description);

        roomsRepository.save(room);
    }

    public void deleteRoom(Integer roomId) {
        roomsRepository.deleteById(roomId);
    }
}