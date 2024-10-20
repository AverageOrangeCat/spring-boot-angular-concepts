package spring.boot.angular.concepts.backend.shared;

public class Constant {

    // Session

    public static final Integer EXPIRATION_TIME_BUFFER = 2 * 24 * 60 * 60;

    public static final Integer COOKIE_RENEWAL_TIME_BUFFER = 1 * 24 * 60 * 60;

    // Validation

    public static final String VALID_AUTHENTICATION_COOKIE = "^[a-f0-9]+$";

}
