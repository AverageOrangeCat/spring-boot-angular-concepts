package spring.boot.angular.concepts.backend.api.controllers.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.angular.concepts.backend.services.credentials.CredentialCreateModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialDeleteModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialGetModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialModel;
import spring.boot.angular.concepts.backend.services.credentials.CredentialService;
import spring.boot.angular.concepts.backend.services.credentials.CredentialUpdateModel;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.exceptions.UnauthorizedException;

@RestController
@RequestMapping("/api/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @GetMapping("/{email}")
    public CredentialModel getCredential(
            @CookieValue("authentication") String authenticationToken,
            @PathVariable("email") String email)
            throws NotFoundException, InternalServerException {

        var credentialGetModel = new CredentialGetModel();

        credentialGetModel.setAuthenticationToken(authenticationToken);

        credentialGetModel.setEmail(email);

        return credentialService.getCredential(credentialGetModel);
    }

    @PostMapping("/create")
    public void createCredential(@RequestBody CredentialCreateForm credentialCreateForm)
            throws NotFoundException, ConflictException, InternalServerException {

        var credentialCreateModel = new CredentialCreateModel();

        credentialCreateModel.setEmail(credentialCreateForm.getEmail());

        credentialCreateModel.setPassword(credentialCreateForm.getPassword());

        credentialCreateModel.setFirstName(credentialCreateForm.getFirstName());

        credentialCreateModel.setLastName(credentialCreateForm.getLastName());

        credentialCreateModel.setBirthDate(credentialCreateForm.getBirthDate());

        credentialCreateModel.setAddress(credentialCreateForm.getAddress());

        credentialCreateModel.setHouseNumber(credentialCreateForm.getHouseNumber());

        credentialCreateModel.setPostalCode(credentialCreateForm.getPostalCode());

        credentialCreateModel.setCity(credentialCreateForm.getCity());

        credentialCreateModel.setCountry(credentialCreateForm.getCountry());

        credentialService.createCredential(credentialCreateModel);
    }

    @PutMapping("/update")
    public void updateCredential(
            @CookieValue("authentication") String authenticationToken,
            @RequestBody CredentialUpdateForm credentialUpdateForm)
            throws UnauthorizedException, ConflictException, InternalServerException {

        var credentialUpdateModel = new CredentialUpdateModel();

        credentialUpdateModel.setAuthenticationToken(authenticationToken);

        credentialUpdateModel.setEmail(credentialUpdateForm.getEmail());

        credentialUpdateModel.setPassword(credentialUpdateForm.getPassword());

        credentialUpdateModel.setFirstName(credentialUpdateForm.getFirstName());

        credentialUpdateModel.setLastName(credentialUpdateForm.getLastName());

        credentialUpdateModel.setBirthDate(credentialUpdateForm.getBirthDate());

        credentialUpdateModel.setAddress(credentialUpdateForm.getAddress());

        credentialUpdateModel.setHouseNumber(credentialUpdateForm.getHouseNumber());

        credentialUpdateModel.setPostalCode(credentialUpdateForm.getPostalCode());

        credentialUpdateModel.setCity(credentialUpdateForm.getCity());

        credentialUpdateModel.setCountry(credentialUpdateForm.getCountry());

        credentialService.updateCredential(credentialUpdateModel);
    }

    @DeleteMapping("/delete")
    public void deleteCredential(@CookieValue("authentication") String authenticationToken)
            throws UnauthorizedException, InternalServerException {

        var credentialDeleteModel = new CredentialDeleteModel();

        credentialDeleteModel.setAuthenticationToken(authenticationToken);

        credentialService.deleteCredential(credentialDeleteModel);
    }

}
