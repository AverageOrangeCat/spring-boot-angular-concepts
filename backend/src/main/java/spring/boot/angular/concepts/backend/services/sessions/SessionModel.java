package spring.boot.angular.concepts.backend.services.sessions;

public class SessionModel {

    private String sessionToken;

    private Long expirationUnixDate;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Long getExpirationUnixDate() {
        return expirationUnixDate;
    }

    public void setExpirationUnixDate(Long expirationUnixDate) {
        this.expirationUnixDate = expirationUnixDate;
    }

}
