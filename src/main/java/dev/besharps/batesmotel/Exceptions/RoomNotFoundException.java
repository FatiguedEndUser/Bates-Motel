package dev.besharps.batesmotel.Exceptions;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException() {
      super("Room not found");
    }
}
