package spring.boot.angular.concepts.backend.services.authenticated_sessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.angular.concepts.backend.infrastructure.repositories.AuthenticatedSessionRepository;
import spring.boot.angular.concepts.backend.infrastructure.repositories.CredentialsRepository;
import spring.boot.angular.concepts.backend.infrastructure.repositories.SessionRepository;
import spring.boot.angular.concepts.backend.services.credentials.CredentialsModel;
import spring.boot.angular.concepts.backend.services.sessions.SessionModel;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.exceptions.UnauthorizedException;
import spring.boot.angular.concepts.backend.shared.utils.CryptoUtils;

@Service
public class AuthenticatedSessionService {

    @Autowired
    private CryptoUtils cryptoUtils;

    @Autowired
    private AuthenticatedSessionRepository authenticatedSessionRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public AuthenticatedSessionModel createAuthenticatedSession(String sessionToken, AuthenticatedSessionCreateModel authenticatedSessionCreateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        try {
            var sessionView = sessionRepository.getSession(sessionToken);
            var credentialsView = credentialsRepository.getCredential(authenticatedSessionCreateModel.getEmail());
            var passwordHash = cryptoUtils.generateSha256Hash(credentialsView.getPasswordSalt() + authenticatedSessionCreateModel.getPassword());

            if (!credentialsView.getPasswordHash().equals(passwordHash)) {
                throw new UnauthorizedException("Invalid login attempt");
            }

            var authenticatedSessionView = new AuthenticatedSessionView();

            authenticatedSessionView.setSessionView(sessionView);
            authenticatedSessionView.setCredentialsView(credentialsView);

            var createdAuthenticatedSessionView = authenticatedSessionRepository.createAuthenticatedSession(authenticatedSessionView);
            var createdAuthenticatedSessionModel = new AuthenticatedSessionModel();

            var linkedSessionView = createdAuthenticatedSessionView.getSessionView();
            var linkedSessionModel = new SessionModel();

            var linkedCredentialsView = createdAuthenticatedSessionView.getCredentialsView();
            var linkedCredentialsModel = new CredentialsModel();

            // Session

            linkedSessionModel.setSessionToken(linkedSessionView.getSessionToken());
            linkedSessionModel.setExpirationUnixDate(linkedSessionView.getExpirationUnixDate());

            // Credentials

            linkedCredentialsModel.setEmail(linkedCredentialsView.getEmail());
            linkedCredentialsModel.setFirstName(linkedCredentialsView.getFirstName());
            linkedCredentialsModel.setLastName(linkedCredentialsView.getLastName());
            linkedCredentialsModel.setBirthDate(linkedCredentialsView.getBirthDate());
            linkedCredentialsModel.setAddress(linkedCredentialsView.getAddress());
            linkedCredentialsModel.setHouseNumber(linkedCredentialsView.getHouseNumber());
            linkedCredentialsModel.setPostalCode(linkedCredentialsView.getPostalCode());
            linkedCredentialsModel.setCity(linkedCredentialsView.getCity());
            linkedCredentialsModel.setCountry(linkedCredentialsView.getCountry());

            // Authenticated Session

            createdAuthenticatedSessionModel.setSessionModel(linkedSessionModel);
            createdAuthenticatedSessionModel.setCredentialsModel(linkedCredentialsModel);

            return createdAuthenticatedSessionModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Invalid login attempt");
        }
    }

    public AuthenticatedSessionModel deleteAuthenticatedSession(String sessionToken, String email)
            throws UnauthorizedException, InternalServerException {

        try {
            var authenticatedSessionView = authenticatedSessionRepository.getAuthenticatedSession(sessionToken, email);

            var deletedAuthenticatedSessionView = authenticatedSessionRepository.deleteAuthenticatedSession(authenticatedSessionView);
            var deletedAuthenticatedSessionModel = new AuthenticatedSessionModel();

            var linkedSessionView = deletedAuthenticatedSessionView.getSessionView();
            var linkedSessionModel = new SessionModel();

            var linkedCredentialsView = deletedAuthenticatedSessionView.getCredentialsView();
            var linkedCredentialsModel = new CredentialsModel();

            // Session

            linkedSessionModel.setSessionToken(linkedSessionView.getSessionToken());
            linkedSessionModel.setExpirationUnixDate(linkedSessionView.getExpirationUnixDate());

            // Credentials

            linkedCredentialsModel.setEmail(linkedCredentialsView.getEmail());
            linkedCredentialsModel.setFirstName(linkedCredentialsView.getFirstName());
            linkedCredentialsModel.setLastName(linkedCredentialsView.getLastName());
            linkedCredentialsModel.setBirthDate(linkedCredentialsView.getBirthDate());
            linkedCredentialsModel.setAddress(linkedCredentialsView.getAddress());
            linkedCredentialsModel.setHouseNumber(linkedCredentialsView.getHouseNumber());
            linkedCredentialsModel.setPostalCode(linkedCredentialsView.getPostalCode());
            linkedCredentialsModel.setCity(linkedCredentialsView.getCity());
            linkedCredentialsModel.setCountry(linkedCredentialsView.getCountry());

            // Authenticated Session

            deletedAuthenticatedSessionModel.setSessionModel(linkedSessionModel);
            deletedAuthenticatedSessionModel.setCredentialsModel(linkedCredentialsModel);

            return deletedAuthenticatedSessionModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

}
