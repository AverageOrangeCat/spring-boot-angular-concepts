package spring.boot.angular.concepts.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.angular.concepts.backend.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class DefaultController {

    @RequestMapping("/**")
    public void getAccountData(HttpServletRequest request) throws NotFoundException {
        throw new NotFoundException("Could not find path: '" + request.getRequestURI() + "'");
    }

}
