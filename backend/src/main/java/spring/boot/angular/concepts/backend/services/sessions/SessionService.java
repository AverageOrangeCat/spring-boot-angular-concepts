package spring.boot.angular.concepts.backend.services.sessions;

import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.angular.concepts.backend.infrastructure.repositories.CredentialRepository;
import spring.boot.angular.concepts.backend.infrastructure.repositories.SessionRepository;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.exceptions.UnauthorizedException;
import spring.boot.angular.concepts.backend.shared.utils.CryptoUtils;

@Service
public class SessionService {

    @Autowired
    private CryptoUtils cryptoUtils;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    public SessionModel getSession(SessionGetModel sessionGetModel) throws NotFoundException, InternalServerException {
        var sessionView = sessionRepository.getSession(sessionGetModel.getAuthenticationToken());
        var sessionModel = new SessionModel();

        sessionModel.setAuthenticationToken(sessionView.getAuthenticationToken());

        sessionModel.setExpirationDate(sessionView.getExpirationDate());

        sessionModel.setExpirationTime(sessionView.getExpirationTime());

        return sessionModel;
    }

    public SessionModel createSession(SessionCreateModel sessionCreateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        try {
            var credentialView = credentialRepository.getCredential(sessionCreateModel.getEmail());
            var passwordHash = cryptoUtils
                    .generateSha256Hash(credentialView.getPasswordSalt() + sessionCreateModel.getPassword());

            if (!credentialView.getPasswordHash().equals(passwordHash)) {
                throw new UnauthorizedException("Invalid login attempt");
            }

            var authenticationToken = cryptoUtils.generateSecureRandomBytes(128);
            var sessionView = new SessionView();
            var sessionModel = new SessionModel();

            sessionView.setCredentialView(credentialView);

            sessionView.setAuthenticationToken(authenticationToken);

            sessionView.setExpirationDate(new Date(0L));

            sessionView.setExpirationTime(new Time(0L));

            sessionView = sessionRepository.createSession(sessionView);

            sessionModel.setAuthenticationToken(sessionView.getAuthenticationToken());

            sessionModel.setExpirationDate(sessionView.getExpirationDate());

            sessionModel.setExpirationTime(sessionView.getExpirationTime());

            return sessionModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Invalid login attempt");
        }
    }

    public SessionModel updateSession(SessionUpdateModel sessionUpdateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        try {
            var sessionView = sessionRepository.getSession(sessionUpdateModel.getAuthenticationToken());
            var sessionModel = new SessionModel();

            sessionView.setExpirationDate(new Date(0L));

            sessionView.setExpirationTime(new Time(0L));

            sessionView = sessionRepository.updateSession(sessionView);

            sessionModel.setAuthenticationToken(sessionView.getAuthenticationToken());

            sessionModel.setExpirationDate(sessionView.getExpirationDate());

            sessionModel.setExpirationTime(sessionView.getExpirationTime());

            return sessionModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

    public SessionModel deleteSession(SessionDeleteModel sessionDeleteModel)
            throws UnauthorizedException, InternalServerException {

        try {
            var sessionView = sessionRepository.getSession(sessionDeleteModel.getAuthenticationToken());
            var sessionModel = new SessionModel();

            sessionView = sessionRepository.deleteSession(sessionView);

            sessionModel.setAuthenticationToken(sessionView.getAuthenticationToken());

            sessionModel.setExpirationDate(sessionView.getExpirationDate());

            sessionModel.setExpirationTime(sessionView.getExpirationTime());

            return sessionModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

}
