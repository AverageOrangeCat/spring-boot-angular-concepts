package spring.boot.angular.concepts.backend.api.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.boot.angular.concepts.backend.services.sessions.SessionService;
import spring.boot.angular.concepts.backend.shared.Constants;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;

public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws InternalServerException, ConflictException, NotFoundException {

        var sessionTokenCookie = WebUtils.getCookie(request, "sessionToken");

        if (sessionTokenCookie == null) {
            var createdSessionModel = sessionService.createSession();
            var newSessionTokenCookie = new Cookie("sessionToken", createdSessionModel.getSessionToken());

            request.setAttribute("sessionToken", createdSessionModel.getSessionToken());

            newSessionTokenCookie.setMaxAge(Constants.EXPIRATION_TIME_BUFFER);
            newSessionTokenCookie.setHttpOnly(true);

            response.addCookie(newSessionTokenCookie);
            return true;
        } 
        
        if (sessionTokenCookie.getMaxAge() < Constants.COOKIE_RENEWAL_TIME_BUFFER) {
            var updatedSessionModel = sessionService.updateSession(sessionTokenCookie.getValue());
            var newSessionTokenCookie = new Cookie("sessionToken", updatedSessionModel.getSessionToken());

            request.setAttribute("sessionToken", updatedSessionModel.getSessionToken());

            newSessionTokenCookie.setMaxAge(Constants.EXPIRATION_TIME_BUFFER);
            newSessionTokenCookie.setHttpOnly(true);

            response.addCookie(newSessionTokenCookie);
            return true;
        }

        request.setAttribute("sessionToken", sessionTokenCookie.getValue());
        return true;
    }

}
