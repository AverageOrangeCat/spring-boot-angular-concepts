package spring.boot.angular.concepts.backend.services.sessions;

public class SessionView {

    private Long id;

    private String sessionToken;

    private Long expirationUnixDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
