package spring.boot.angular.concepts.backend.services.sessions;

import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.angular.concepts.backend.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.exceptions.UnauthorizedException;
import spring.boot.angular.concepts.backend.repositories.CredentialRepository;
import spring.boot.angular.concepts.backend.repositories.SessionRepository;
import spring.boot.angular.concepts.backend.utils.crypto.CryptoUtils;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    public SessionModel createSession(SessionCreateModel sessionCreateModel)
            throws UnauthorizedException, ConflictException, InternalServerException {

        try {
            var credentialView = credentialRepository.getCredential(sessionCreateModel.getEmail());
            var passwordHash = CryptoUtils
                    .generateSha256Hash(credentialView.getPasswordSalt() + sessionCreateModel.getPassword());

            if (!credentialView.getPasswordHash().equals(passwordHash)) {
                throw new UnauthorizedException("Invalid login attempt");
            }

            var accessToken = CryptoUtils.generateSecureRandomBytes(128);
            var sessionView = new SessionView();
            var sessionModel = new SessionModel();

            sessionView.setCredentialView(credentialView);

            sessionView.setAccessToken(accessToken);

            sessionView.setExpirationDate(new Date(0L));

            sessionView.setExpirationTime(new Time(0L));

            sessionView = sessionRepository.createSession(sessionView);

            sessionModel.setAccessToken(sessionView.getAccessToken());

            sessionModel.setExpirationDate(sessionView.getExpirationDate());

            sessionModel.setExpirationTime(sessionView.getExpirationTime());

            return sessionModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Invalid login attempt");
        }
    }

    public SessionModel deleteSession(SessionDeleteModel sessionDeleteModel) throws UnauthorizedException, InternalServerException {
        try {
            var sessionView = sessionRepository.getSession(sessionDeleteModel.getAccessToken());
            var sessionModel = new SessionModel();

            sessionView = sessionRepository.deleteSession(sessionView);

            sessionModel.setAccessToken(sessionView.getAccessToken());

            sessionModel.setExpirationDate(sessionView.getExpirationDate());

            sessionModel.setExpirationTime(sessionView.getExpirationTime());

            return sessionModel;

        } catch (NotFoundException exception) {
            throw new UnauthorizedException("Authentication failed");
        }
    }

}
