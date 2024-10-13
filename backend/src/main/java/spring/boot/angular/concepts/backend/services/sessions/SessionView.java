package spring.boot.angular.concepts.backend.services.sessions;

import java.sql.Date;
import java.sql.Time;

import spring.boot.angular.concepts.backend.services.credentials.CredentialView;

public class SessionView {
    
    private Long id;

    private CredentialView credentialView;

    private String accessToken;

    private Date expirationDate;

    private Time expirationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CredentialView getCredentialView() {
        return credentialView;
    }

    public void setCredentialView(CredentialView credentialView) {
        this.credentialView = credentialView;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Time getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Time expirationTime) {
        this.expirationTime = expirationTime;
    }

}
