package spring.boot.angular.concepts.backend.services.sessions;

public class SessionGetModel {

    private String authenticationToken;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

}
