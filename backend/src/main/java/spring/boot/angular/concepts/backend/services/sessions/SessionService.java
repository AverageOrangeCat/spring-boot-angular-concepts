package spring.boot.angular.concepts.backend.services.sessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.angular.concepts.backend.infrastructure.repositories.SessionRepository;
import spring.boot.angular.concepts.backend.shared.Constants;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;
import spring.boot.angular.concepts.backend.shared.utils.CryptoUtils;
import spring.boot.angular.concepts.backend.shared.utils.TimeUtils;

@Service
public class SessionService {

    @Autowired
    private CryptoUtils cryptoUtils;

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private SessionRepository sessionRepository;

    public SessionModel createSession() throws InternalServerException, ConflictException {
        var sessionToken = cryptoUtils.generateSecureRandomBytes(128);
        var expirationUnixDate = timeUtils.unixDateNow() + Constants.EXPIRATION_TIME_BUFFER;
        var sessionView = new SessionView();

        sessionView.setSessionToken(sessionToken);
        sessionView.setExpirationUnixDate(expirationUnixDate);

        var createdSessionView = sessionRepository.createSession(sessionView);
        var createdSessionModel = new SessionModel();

        createdSessionModel.setSessionToken(createdSessionView.getSessionToken());
        createdSessionModel.setExpirationUnixDate(createdSessionView.getExpirationUnixDate());

        return createdSessionModel;
    }

    public SessionModel updateSession(String sessionToken)
            throws InternalServerException, NotFoundException, ConflictException {

        var newSessionToken = cryptoUtils.generateSecureRandomBytes(128);
        var newExpirationUnixDate = timeUtils.unixDateNow() + Constants.EXPIRATION_TIME_BUFFER;
        var sessionView = sessionRepository.getSession(sessionToken);

        sessionView.setSessionToken(newSessionToken);
        sessionView.setExpirationUnixDate(newExpirationUnixDate);

        var updatedSessionView = sessionRepository.updateSession(sessionView);
        var updatedSessionModel = new SessionModel();

        updatedSessionModel.setSessionToken(updatedSessionView.getSessionToken());
        updatedSessionModel.setExpirationUnixDate(updatedSessionView.getExpirationUnixDate());

        return updatedSessionModel;

    }

    public SessionModel deleteSession(String sessionToken)
            throws NotFoundException, InternalServerException {

        var sessionView = sessionRepository.getSession(sessionToken);
        var deletedSessionView = sessionRepository.deleteSession(sessionView);
        var deletedSessionModel = new SessionModel();

        deletedSessionModel.setSessionToken(deletedSessionView.getSessionToken());
        deletedSessionModel.setExpirationUnixDate(deletedSessionView.getExpirationUnixDate());

        return deletedSessionModel;
    }

}
