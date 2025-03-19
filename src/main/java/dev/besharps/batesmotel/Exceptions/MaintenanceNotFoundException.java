package dev.besharps.batesmotel.Exceptions;

public class MaintenanceNotFoundException extends RuntimeException {
    public MaintenanceNotFoundException() {

        super("Maintenance log not found");
    }
}
