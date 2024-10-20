package spring.boot.angular.concepts.backend.shared.exceptions;

public class InternalServerException extends Exception {

    public InternalServerException() {
        super("Not specified");
    }

    public InternalServerException(String message) {
        super(message);
    }

}
