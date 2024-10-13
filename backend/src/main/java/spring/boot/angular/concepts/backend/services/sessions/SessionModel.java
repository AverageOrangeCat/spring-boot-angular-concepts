package spring.boot.angular.concepts.backend.services.sessions;

import java.sql.Date;
import java.sql.Time;

public class SessionModel {
    
    private String accessToken;

    private Date expirationDate;

    private Time expirationTime;

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
