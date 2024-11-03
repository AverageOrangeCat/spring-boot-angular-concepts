package spring.boot.angular.concepts.backend.infrastructure.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spring.boot.angular.concepts.backend.services.authenticated_sessions.AuthenticatedSessionView;
import spring.boot.angular.concepts.backend.services.credentials.CredentialsView;
import spring.boot.angular.concepts.backend.services.sessions.SessionView;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;

@Repository
public class AuthenticatedSessionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(SessionRepository.class);

    private final RowMapper<AuthenticatedSessionView> rowMapper = (resultSet, rowNumber) -> {
        var credentialsView = new CredentialsView();
        var sessionView = new SessionView();
        var authenticatedSessionView = new AuthenticatedSessionView();

        // Session

        sessionView.setId(resultSet.getLong("session_id"));
        sessionView.setSessionToken(resultSet.getString("session_token"));
        sessionView.setExpirationUnixDate(resultSet.getLong("expiration_unix_date"));

        // Credentials

        credentialsView.setId(resultSet.getLong("credential_id"));
        credentialsView.setEmail(resultSet.getString("email"));
        credentialsView.setPasswordSalt(resultSet.getString("password_salt"));
        credentialsView.setPasswordHash(resultSet.getString("password_hash"));
        credentialsView.setFirstName(resultSet.getString("first_name"));
        credentialsView.setLastName(resultSet.getString("last_name"));
        credentialsView.setBirthDate(resultSet.getDate("birth_date"));
        credentialsView.setAddress(resultSet.getString("address"));
        credentialsView.setHouseNumber(resultSet.getString("house_number"));
        credentialsView.setPostalCode(resultSet.getString("postal_code"));
        credentialsView.setCity(resultSet.getString("city"));
        credentialsView.setCountry(resultSet.getString("country"));

        // Authenticated Session

        authenticatedSessionView.setId(resultSet.getLong("active_session_id"));
        authenticatedSessionView.setSessionView(sessionView);
        authenticatedSessionView.setCredentialsView(credentialsView);

        return authenticatedSessionView;
    };

    public AuthenticatedSessionView getAuthenticatedSession(String sessionToken, String email)
            throws NotFoundException, InternalServerException {

        var query = """
                SELECT * FROM sessions
                LEFT JOIN authenticated_sessions ON authenticated_sessions.session_id = sessions.session_id
                LEFT JOIN credentials ON authenticated_sessions.credentials_id = credentials.credentials_id
                WHERE session_token = ?
                AND email = ?
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper, sessionToken, email);

        } catch (EmptyResultDataAccessException exception) {
            logger.warn(exception.getMessage(), exception);
            throw new NotFoundException("Could not find session: '" + sessionToken + "'");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public AuthenticatedSessionView createAuthenticatedSession(AuthenticatedSessionView authenticatedSessionView)
            throws ConflictException, InternalServerException {

        var query = """
                WITH returning_sessions AS (
                    INSERT INTO authenticated_sessions (
                        credentials_id,
                        session_id,
                    )
                    VALUES (?, ?)
                    RETURNING *
                )
                SELECT *
                FROM returning_sessions
                LEFT JOIN authenticated_sessions ON authenticated_sessions.session_id = sessions.session_id
                LEFT JOIN credentials ON authenticated_sessions.credentials_id = credentials.credentials_id
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper,
                    authenticatedSessionView
                            .getCredentialsView()
                            .getId(),

                    authenticatedSessionView
                            .getSessionView()
                            .getId());

        } catch (DataIntegrityViolationException exception) {
            var sessionToken = authenticatedSessionView
                    .getSessionView()
                    .getSessionToken();

            logger.error(exception.getMessage(), exception);
            throw new ConflictException("Session '" + sessionToken + "' is already authenticated");

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public AuthenticatedSessionView deleteAuthenticatedSession(AuthenticatedSessionView authenticatedSessionView)
            throws InternalServerException {

        var query = """
                WITH returning_sessions AS (
                    DELETE FROM authenticated_sessions
                    WHERE authenticated_session_id = ?
                )
                SELECT *
                FROM returning_sessions
                LEFT JOIN authenticated_sessions ON authenticated_sessions.session_id = sessions.session_id
                LEFT JOIN credentials ON authenticated_sessions.credentials_id = credentials.credentials_id
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper, authenticatedSessionView.getId());

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

}
