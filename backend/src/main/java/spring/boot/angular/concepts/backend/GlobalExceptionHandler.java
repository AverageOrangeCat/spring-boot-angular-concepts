package spring.boot.angular.concepts.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import spring.boot.angular.concepts.backend.infrastructure.repositories.CredentialsRepository;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.exceptions.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(CredentialsRepository.class);

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GlobalExceptionResponse handleUnauthorizedException(UnauthorizedException exception) {
        logger.warn(exception.getMessage(), exception);

        var response = new GlobalExceptionResponse();

        response.setHttpStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(exception.getMessage());

        return response;
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public GlobalExceptionResponse handleConflictException(ConflictException exception) {
        logger.warn(exception.getMessage(), exception);

        var response = new GlobalExceptionResponse();

        response.setHttpStatus(HttpStatus.CONFLICT);
        response.setMessage(exception.getMessage());

        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalExceptionResponse handleNotFoundException(NotFoundException exception) {
        logger.warn(exception.getMessage(), exception);
        
        var response = new GlobalExceptionResponse();

        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setMessage(exception.getMessage());

        return response;
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalExceptionResponse handleInternalServerException(InternalServerException exception) {
        logger.warn(exception.getMessage(), exception);
        
        var response = new GlobalExceptionResponse();

        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(exception.getMessage());

        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalExceptionResponse handleOtherException(Exception exception) {
        logger.warn(exception.getMessage(), exception);
        
        var response = new GlobalExceptionResponse();

        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(exception.getMessage());

        return response;
    }

    // Return error for not found resource requests
    // Normally would be forwarded to frontend application

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalExceptionResponse handleNoResourceFoundException(Exception exception) {
        logger.warn(exception.getMessage(), exception);
        
        var response = new GlobalExceptionResponse();

        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setMessage(exception.getMessage());

        return response;
    }

}
