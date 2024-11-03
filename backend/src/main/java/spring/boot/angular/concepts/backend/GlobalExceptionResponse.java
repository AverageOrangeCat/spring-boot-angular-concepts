package spring.boot.angular.concepts.backend;

import org.springframework.http.HttpStatus;

public class GlobalExceptionResponse {

    private Integer statusCode = 500;
    private String message = "";

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Special method required in 'GlobalExceptionHandler.java'

    public void setHttpStatus(HttpStatus httpStatus) {
        setStatusCode(httpStatus.value());
    }

}
