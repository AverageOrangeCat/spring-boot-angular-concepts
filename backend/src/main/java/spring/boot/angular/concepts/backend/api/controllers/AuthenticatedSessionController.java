package spring.boot.angular.concepts.backend.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.angular.concepts.backend.services.authenticated_sessions.AuthenticatedSessionCreateModel;
import spring.boot.angular.concepts.backend.services.authenticated_sessions.AuthenticatedSessionService;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.exceptions.UnauthorizedException;

@RestController
@RequestMapping("/api/session")
public class AuthenticatedSessionController {

    @Autowired
    private AuthenticatedSessionService authenticatedSessionService;

    @PostMapping("/login")
    public void createCredential(@RequestAttribute String sessionToken, @RequestBody AuthenticatedSessionCreateModel authenticatedSessionCreateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        authenticatedSessionService.createAuthenticatedSession(sessionToken, authenticatedSessionCreateModel);
    }

    @DeleteMapping("/logout/{email}")
    public void deleteCredential(@RequestAttribute String sessionToken, @PathVariable String email)
            throws UnauthorizedException, InternalServerException, NotFoundException {

        authenticatedSessionService.deleteAuthenticatedSession(sessionToken, email);
    }

}
