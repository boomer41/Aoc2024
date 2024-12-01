package net.boomer41.aoc2024.day1;

public class InvalidInputException extends Exception {

    InvalidInputException(String message) {
        super(message);
    }

    InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

}
