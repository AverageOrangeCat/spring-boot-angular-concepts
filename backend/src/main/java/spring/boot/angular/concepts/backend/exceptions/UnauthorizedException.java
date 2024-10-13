package spring.boot.angular.concepts.backend.exceptions;

public class UnauthorizedException extends Exception {

    public UnauthorizedException() {
        super("Not specified");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}
