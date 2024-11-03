package spring.boot.angular.concepts.backend.services.authenticated_sessions;

import spring.boot.angular.concepts.backend.services.credentials.CredentialsModel;
import spring.boot.angular.concepts.backend.services.sessions.SessionModel;

public class AuthenticatedSessionModel {

    private SessionModel sessionModel;

    private CredentialsModel credentialsModel;

    public SessionModel getSessionModel() {
        return sessionModel;
    }

    public void setSessionModel(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public CredentialsModel getCredentialsModel() {
        return credentialsModel;
    }

    public void setCredentialsModel(CredentialsModel credentialsModel) {
        this.credentialsModel = credentialsModel;
    }

}
