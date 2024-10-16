package spring.boot.angular.concepts.backend.services.credentials;

public class CredentialGetModel {

    private String authenticationToken;

    private String email;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
