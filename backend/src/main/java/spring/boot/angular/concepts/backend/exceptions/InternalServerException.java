package spring.boot.angular.concepts.backend.exceptions;

public class InternalServerException extends Exception {

    public InternalServerException() {
        super("Not specified");
    }

    public InternalServerException(String message) {
        super(message);
    }

}
