package spring.boot.angular.concepts.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.angular.concepts.backend.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.exceptions.UnauthorizedException;
import spring.boot.angular.concepts.backend.services.sessions.SessionCreateModel;
import spring.boot.angular.concepts.backend.services.sessions.SessionDeleteModel;
import spring.boot.angular.concepts.backend.services.sessions.SessionModel;
import spring.boot.angular.concepts.backend.services.sessions.SessionService;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/create")
    public SessionModel createSession(@RequestBody SessionCreateModel sessionCreateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        return sessionService.createSession(sessionCreateModel);
    }

    @DeleteMapping("/delete")
    public SessionModel deleteSession(@RequestBody SessionDeleteModel sessionDeleteModel)
            throws UnauthorizedException, InternalServerException {

        return sessionService.deleteSession(sessionDeleteModel);
    }

}
