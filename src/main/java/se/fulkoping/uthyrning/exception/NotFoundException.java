package se.fulkoping.uthyrning.exception;



public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
