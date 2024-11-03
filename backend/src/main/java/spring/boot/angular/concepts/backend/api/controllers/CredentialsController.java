package spring.boot.angular.concepts.backend.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.angular.concepts.backend.services.credentials.CredentialsCreateModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialsModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialsService;
import spring.boot.angular.concepts.backend.services.credentials.CredentialsUpdateModel;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.exceptions.UnauthorizedException;

@RestController
@RequestMapping("/api/credentials")
public class CredentialsController {

    @Autowired
    private CredentialsService credentialService;

    @GetMapping("/{email}")
    public CredentialsModel getCredential(@PathVariable String email)
            throws NotFoundException, InternalServerException {

        return credentialService.getCredentials(email);
    }

    @PostMapping("/create")
    public void createCredential(@RequestBody CredentialsCreateModel credentialsCreateModel)
            throws ConflictException, InternalServerException {

        credentialService.createCredentials(credentialsCreateModel);
    }

    @PutMapping("/update/{email}")
    public void updateCredential(@RequestAttribute String sessionToken, @PathVariable String email, @RequestBody CredentialsUpdateModel credentialsUpdateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        credentialService.updateCredentials(sessionToken, email, credentialsUpdateModel);
    }

    @DeleteMapping("/delete/{email}")
    public void deleteCredential(@RequestAttribute String sessionToken, @PathVariable String email)
            throws UnauthorizedException, InternalServerException {

        credentialService.deleteCredentials(sessionToken, email);
    }

}
