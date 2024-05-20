package com.dms.variant.exceptions;

public class InvalidUserInput extends RuntimeException {
    public InvalidUserInput(String message) {
        super("Invalid User Input : " + message);
    }
}
