package dev.besharps.batesmotel.Exceptions;

public class LastNameNotFound extends RuntimeException {
    public LastNameNotFound() {
        super("Last name not found: ");
    }
}
