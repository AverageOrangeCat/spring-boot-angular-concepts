package spring.boot.angular.concepts.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.angular.concepts.backend.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.exceptions.UnauthorizedException;
import spring.boot.angular.concepts.backend.services.credentials.CredentialCreateModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialDeleteModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialService;
import spring.boot.angular.concepts.backend.services.credentials.CredentialUpdateModel;

@RestController
@RequestMapping("/api/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @GetMapping("/{email}")
    public CredentialModel getCredential(@PathVariable("email") String email)
            throws NotFoundException, InternalServerException {

        return credentialService.getCredential(email);
    }

    @PostMapping("/create")
    public CredentialModel createCredential(@RequestBody CredentialCreateModel credentialsCreateModel)
            throws NotFoundException, ConflictException, InternalServerException {

        return credentialService.createCredential(credentialsCreateModel);
    }

    @PutMapping("/update")
    public CredentialModel updateCredential(@RequestBody CredentialUpdateModel credentialUpdateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        return credentialService.updateCredential(credentialUpdateModel);
    }

    @DeleteMapping("/delete")
    public CredentialModel deleteCredential(@RequestBody CredentialDeleteModel credentialDeleteModel)
            throws UnauthorizedException, InternalServerException {

        return credentialService.deleteCredential(credentialDeleteModel);
    }

}
