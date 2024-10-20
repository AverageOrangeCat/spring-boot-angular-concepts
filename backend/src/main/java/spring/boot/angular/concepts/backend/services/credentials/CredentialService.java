package spring.boot.angular.concepts.backend.services.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.angular.concepts.backend.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.exceptions.UnauthorizedException;
import spring.boot.angular.concepts.backend.repositories.CredentialRepository;
import spring.boot.angular.concepts.backend.repositories.SessionRepository;
import spring.boot.angular.concepts.backend.utils.CryptoUtils;

@Service
public class CredentialService {

    @Autowired
    private CryptoUtils cryptoUtils;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public CredentialModel getCredential(CredentialGetModel credentialGetModel)
            throws NotFoundException, InternalServerException {

        var credentialView = credentialRepository.getCredential(credentialGetModel.getEmail());
        var credentialModel = new CredentialModel();

        credentialModel.setEmail(credentialView.getEmail());

        credentialModel.setFirstName(credentialView.getFirstName());

        credentialModel.setLastName(credentialView.getLastName());

        credentialModel.setBirthDate(credentialView.getBirthDate());

        credentialModel.setAddress(credentialView.getAddress());

        credentialModel.setHouseNumber(credentialView.getHouseNumber());

        credentialModel.setPostalCode(credentialView.getPostalCode());

        credentialModel.setCity(credentialView.getCity());

        credentialModel.setCountry(credentialView.getCountry());

        return credentialModel;
    }

    public CredentialModel createCredential(CredentialCreateModel credentialCreateModel)
            throws ConflictException, InternalServerException {

        var passwordSalt = cryptoUtils.generateSecureRandomBytes(16);
        var passwordHash = cryptoUtils.generateSha256Hash(passwordSalt + credentialCreateModel.getPassword());
        var credentialView = new CredentialView();
        var credentialModel = new CredentialModel();

        credentialView.setEmail(credentialCreateModel.getEmail());

        credentialView.setPasswordSalt(passwordSalt);

        credentialView.setPasswordHash(passwordHash);

        credentialView.setFirstName(credentialCreateModel.getFirstName());

        credentialView.setLastName(credentialCreateModel.getLastName());

        credentialView.setBirthDate(credentialCreateModel.getBirthDate());

        credentialView.setAddress(credentialCreateModel.getAddress());

        credentialView.setHouseNumber(credentialCreateModel.getHouseNumber());

        credentialView.setPostalCode(credentialCreateModel.getPostalCode());

        credentialView.setCity(credentialCreateModel.getCity());

        credentialView.setCountry(credentialCreateModel.getCountry());

        credentialView = credentialRepository.createCredential(credentialView);

        credentialModel.setEmail(credentialView.getEmail());

        credentialModel.setFirstName(credentialView.getFirstName());

        credentialModel.setLastName(credentialView.getLastName());

        credentialModel.setBirthDate(credentialView.getBirthDate());

        credentialModel.setAddress(credentialView.getAddress());

        credentialModel.setHouseNumber(credentialView.getHouseNumber());

        credentialModel.setPostalCode(credentialView.getPostalCode());

        credentialModel.setCity(credentialView.getCity());

        credentialModel.setCountry(credentialView.getCountry());

        return credentialModel;
    }

    public CredentialModel updateCredential(CredentialUpdateModel credentialUpdateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        try {
            var sessionView = sessionRepository.getSession(credentialUpdateModel.getAuthenticationToken());
            var credentialView = sessionView.getCredentialView();
            var credentialModel = new CredentialModel();

            if (credentialUpdateModel.getPassword() != null) {
                var passwordSalt = cryptoUtils.generateSecureRandomBytes(16);
                var passwordHash = cryptoUtils.generateSha256Hash(passwordSalt + credentialUpdateModel.getPassword());

                credentialView.setPasswordSalt(passwordSalt);

                credentialView.setPasswordHash(passwordHash);
            }

            credentialView.setEmail(credentialUpdateModel.getEmail());

            credentialView.setFirstName(credentialUpdateModel.getFirstName());

            credentialView.setLastName(credentialUpdateModel.getLastName());

            credentialView.setBirthDate(credentialUpdateModel.getBirthDate());

            credentialView.setAddress(credentialUpdateModel.getAddress());

            credentialView.setHouseNumber(credentialUpdateModel.getHouseNumber());

            credentialView.setPostalCode(credentialUpdateModel.getPostalCode());

            credentialView.setCity(credentialUpdateModel.getCity());

            credentialView.setCountry(credentialUpdateModel.getCountry());

            credentialView = credentialRepository.updateCredential(credentialView);

            credentialModel.setEmail(credentialView.getEmail());

            credentialModel.setFirstName(credentialView.getFirstName());

            credentialModel.setLastName(credentialView.getLastName());

            credentialModel.setBirthDate(credentialView.getBirthDate());

            credentialModel.setAddress(credentialView.getAddress());

            credentialModel.setHouseNumber(credentialView.getHouseNumber());

            credentialModel.setPostalCode(credentialView.getPostalCode());

            credentialModel.setCity(credentialView.getCity());

            credentialModel.setCountry(credentialView.getCountry());

            return credentialModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

    public CredentialModel deleteCredential(CredentialDeleteModel credentialDeleteModel)
            throws UnauthorizedException, InternalServerException {

        try {
            var sessionView = sessionRepository.getSession(credentialDeleteModel.getAuthenticationToken());
            var credentialView = sessionView.getCredentialView();
            var credentialModel = new CredentialModel();

            credentialView = credentialRepository.deleteCredential(credentialView);

            credentialModel.setEmail(credentialView.getEmail());

            credentialModel.setFirstName(credentialView.getFirstName());

            credentialModel.setLastName(credentialView.getLastName());

            credentialModel.setBirthDate(credentialView.getBirthDate());

            credentialModel.setAddress(credentialView.getAddress());

            credentialModel.setHouseNumber(credentialView.getHouseNumber());

            credentialModel.setPostalCode(credentialView.getPostalCode());

            credentialModel.setCity(credentialView.getCity());

            credentialModel.setCountry(credentialView.getCountry());

            return credentialModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

}
