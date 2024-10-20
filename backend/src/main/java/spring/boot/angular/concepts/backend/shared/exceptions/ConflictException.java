package spring.boot.angular.concepts.backend.shared.exceptions;

public class ConflictException extends Exception {

    public ConflictException() {
        super("Not specified");
    }

    public ConflictException(String message) {
        super(message);
    }

}
