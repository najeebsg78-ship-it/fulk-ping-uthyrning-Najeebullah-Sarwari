package se.fulkoping.uthyrning.exception;



public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}