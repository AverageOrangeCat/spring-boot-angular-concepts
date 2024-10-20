package spring.boot.angular.concepts.backend.shared.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("Not specified");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
