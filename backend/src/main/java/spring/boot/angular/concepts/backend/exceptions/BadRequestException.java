package spring.boot.angular.concepts.backend.exceptions;

public class BadRequestException extends Exception {
    
    public BadRequestException() {
        super("Not specified");
    }

    public BadRequestException(String message) {
        super(message);
    }

}
