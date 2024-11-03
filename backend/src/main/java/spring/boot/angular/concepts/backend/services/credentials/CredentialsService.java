package spring.boot.angular.concepts.backend.services.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.angular.concepts.backend.infrastructure.repositories.AuthenticatedSessionRepository;
import spring.boot.angular.concepts.backend.infrastructure.repositories.CredentialsRepository;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.exceptions.UnauthorizedException;
import spring.boot.angular.concepts.backend.shared.utils.CryptoUtils;

@Service
public class CredentialsService {

    @Autowired
    private CryptoUtils cryptoUtils;

    @Autowired
    private AuthenticatedSessionRepository authenticatedSessionRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    public CredentialsModel getCredentials(String email)
            throws NotFoundException, InternalServerException {

        var credentialView = credentialsRepository.getCredential(email);
        var credentialModel = new CredentialsModel();

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

    public CredentialsModel createCredentials(CredentialsCreateModel credentialsCreateModel)
            throws ConflictException, InternalServerException {

        var passwordSalt = cryptoUtils.generateSecureRandomBytes(16);
        var passwordHash = cryptoUtils.generateSha256Hash(passwordSalt + credentialsCreateModel.getPassword());
        var credentialView = new CredentialsView();

        credentialView.setEmail(credentialsCreateModel.getEmail());
        credentialView.setPasswordSalt(passwordSalt);
        credentialView.setPasswordHash(passwordHash);
        credentialView.setFirstName(credentialsCreateModel.getFirstName());
        credentialView.setLastName(credentialsCreateModel.getLastName());
        credentialView.setBirthDate(credentialsCreateModel.getBirthDate());
        credentialView.setAddress(credentialsCreateModel.getAddress());
        credentialView.setHouseNumber(credentialsCreateModel.getHouseNumber());
        credentialView.setPostalCode(credentialsCreateModel.getPostalCode());
        credentialView.setCity(credentialsCreateModel.getCity());
        credentialView.setCountry(credentialsCreateModel.getCountry());

        var createdCredentialView = credentialsRepository.createCredential(credentialView);
        var createdCredentialModel = new CredentialsModel();

        createdCredentialModel.setEmail(createdCredentialView.getEmail());
        createdCredentialModel.setFirstName(createdCredentialView.getFirstName());
        createdCredentialModel.setLastName(createdCredentialView.getLastName());
        createdCredentialModel.setBirthDate(createdCredentialView.getBirthDate());
        createdCredentialModel.setAddress(createdCredentialView.getAddress());
        createdCredentialModel.setHouseNumber(createdCredentialView.getHouseNumber());
        createdCredentialModel.setPostalCode(createdCredentialView.getPostalCode());
        createdCredentialModel.setCity(createdCredentialView.getCity());
        createdCredentialModel.setCountry(createdCredentialView.getCountry());

        return createdCredentialModel;
    }

    public CredentialsModel updateCredentials(String sessionToken, String email, CredentialsUpdateModel credentialsUpdateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        try {
            var authenticatedSessionView = authenticatedSessionRepository.getAuthenticatedSession(sessionToken, email);
            var credentialsView = authenticatedSessionView.getCredentialsView();

            if (credentialsUpdateModel.getPassword() != null) {
                var newPasswordSalt = cryptoUtils.generateSecureRandomBytes(16);
                var newPasswordHash = cryptoUtils.generateSha256Hash(newPasswordSalt + credentialsUpdateModel.getPassword());

                credentialsView.setPasswordSalt(newPasswordSalt);
                credentialsView.setPasswordHash(newPasswordHash);
            }

            credentialsView.setEmail(credentialsUpdateModel.getEmail());
            credentialsView.setFirstName(credentialsUpdateModel.getFirstName());
            credentialsView.setLastName(credentialsUpdateModel.getLastName());
            credentialsView.setBirthDate(credentialsUpdateModel.getBirthDate());
            credentialsView.setAddress(credentialsUpdateModel.getAddress());
            credentialsView.setHouseNumber(credentialsUpdateModel.getHouseNumber());
            credentialsView.setPostalCode(credentialsUpdateModel.getPostalCode());
            credentialsView.setCity(credentialsUpdateModel.getCity());
            credentialsView.setCountry(credentialsUpdateModel.getCountry());

            var updatedCredentialsView = credentialsRepository.updateCredential(credentialsView);
            var updatedcredentialsModel = new CredentialsModel();

            updatedcredentialsModel.setEmail(updatedCredentialsView.getEmail());
            updatedcredentialsModel.setFirstName(updatedCredentialsView.getFirstName());
            updatedcredentialsModel.setLastName(updatedCredentialsView.getLastName());
            updatedcredentialsModel.setBirthDate(updatedCredentialsView.getBirthDate());
            updatedcredentialsModel.setAddress(updatedCredentialsView.getAddress());
            updatedcredentialsModel.setHouseNumber(updatedCredentialsView.getHouseNumber());
            updatedcredentialsModel.setPostalCode(updatedCredentialsView.getPostalCode());
            updatedcredentialsModel.setCity(updatedCredentialsView.getCity());
            updatedcredentialsModel.setCountry(updatedCredentialsView.getCountry());

            return updatedcredentialsModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

    public CredentialsModel deleteCredentials(String sessionToken, String email)
            throws UnauthorizedException, InternalServerException {

        try {
            var authenticatedSessionView = authenticatedSessionRepository.getAuthenticatedSession(sessionToken, email);
            var credentialsView = authenticatedSessionView.getCredentialsView();

            var deletedCredentialsView = credentialsRepository.deleteCredential(credentialsView);
            var deletedCredentialsModel = new CredentialsModel();

            deletedCredentialsModel.setEmail(deletedCredentialsView.getEmail());
            deletedCredentialsModel.setFirstName(deletedCredentialsView.getFirstName());
            deletedCredentialsModel.setLastName(deletedCredentialsView.getLastName());
            deletedCredentialsModel.setBirthDate(deletedCredentialsView.getBirthDate());
            deletedCredentialsModel.setAddress(deletedCredentialsView.getAddress());
            deletedCredentialsModel.setHouseNumber(deletedCredentialsView.getHouseNumber());
            deletedCredentialsModel.setPostalCode(deletedCredentialsView.getPostalCode());
            deletedCredentialsModel.setCity(deletedCredentialsView.getCity());
            deletedCredentialsModel.setCountry(deletedCredentialsView.getCountry());

            return deletedCredentialsModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

}
