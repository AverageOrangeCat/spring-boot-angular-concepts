package spring.boot.angular.concepts.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import spring.boot.angular.concepts.backend.exceptions.NotFoundException;

@RestController
@RequestMapping("/api")
public class DefaultController {

    @RequestMapping("/**")
    public void notFound(HttpServletRequest request) throws NotFoundException {
        throw new NotFoundException("Could not find path: '" + request.getRequestURI() + "'");
    }

}
