package spring.boot.angular.concepts.backend.services.authenticated_sessions;

import spring.boot.angular.concepts.backend.services.credentials.CredentialsView;
import spring.boot.angular.concepts.backend.services.sessions.SessionView;

public class AuthenticatedSessionView {

    private Long id;

    private CredentialsView credentialsView;

    private SessionView sessionView;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CredentialsView getCredentialsView() {
        return credentialsView;
    }

    public void setCredentialsView(CredentialsView credentialsView) {
        this.credentialsView = credentialsView;
    }

    public SessionView getSessionView() {
        return sessionView;
    }

    public void setSessionView(SessionView sessionView) {
        this.sessionView = sessionView;
    }

}
