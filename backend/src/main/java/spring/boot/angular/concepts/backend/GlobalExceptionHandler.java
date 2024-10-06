package spring.boot.angular.concepts.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import spring.boot.angular.concepts.backend.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalExceptionResponse handleNotFoundException(NotFoundException exception) {
        return new GlobalExceptionResponse()
            .setHttpStatus(HttpStatus.NOT_FOUND)
            .setMessage(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalExceptionResponse handleOtherException(Exception exception) {
        return new GlobalExceptionResponse()
            .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .setMessage("An error occured: " + exception.getMessage());
    }

    // Return error for not found resource requests
    // Normally would be forwarded to frontend application

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalExceptionResponse handleNoResourceFoundException(Exception exception) {
        return new GlobalExceptionResponse()
            .setHttpStatus(HttpStatus.NOT_FOUND)
            .setMessage("This application doesn't have a frontend");
    }

}
