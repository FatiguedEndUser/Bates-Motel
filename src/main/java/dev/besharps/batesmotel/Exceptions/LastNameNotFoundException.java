package dev.besharps.batesmotel.Exceptions;

public class LastNameNotFoundException extends RuntimeException {
    public LastNameNotFoundException() {
        super("Last name not found: ");
    }
}
