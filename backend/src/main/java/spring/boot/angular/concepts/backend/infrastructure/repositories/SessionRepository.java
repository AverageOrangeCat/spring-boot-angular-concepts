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

import spring.boot.angular.concepts.backend.services.credentials.CredentialView;
import spring.boot.angular.concepts.backend.services.sessions.SessionView;
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;

@Repository
public class SessionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(SessionRepository.class);

    private final RowMapper<SessionView> rowMapper = (resultSet, rowNumber) -> {
        var credentialView = new CredentialView();

        credentialView.setId(resultSet.getLong("credential_id"));

        credentialView.setEmail(resultSet.getString("email"));

        credentialView.setPasswordSalt(resultSet.getString("password_salt"));

        credentialView.setPasswordHash(resultSet.getString("password_hash"));

        credentialView.setFirstName(resultSet.getString("first_name"));

        credentialView.setLastName(resultSet.getString("last_name"));

        credentialView.setBirthDate(resultSet.getDate("birth_date"));

        credentialView.setAddress(resultSet.getString("address"));

        credentialView.setHouseNumber(resultSet.getString("house_number"));

        credentialView.setPostalCode(resultSet.getString("postal_code"));

        credentialView.setCity(resultSet.getString("city"));

        credentialView.setCountry(resultSet.getString("country"));

        var sessionView = new SessionView();

        sessionView.setId(resultSet.getLong("session_id"));

        sessionView.setCredentialView(credentialView);

        sessionView.setAuthenticationToken(resultSet.getString("authentication_token"));

        sessionView.setExpirationDate(resultSet.getDate("expiration_date"));

        sessionView.setExpirationTime(resultSet.getTime("expiration_time"));

        return sessionView;
    };

    public SessionView getSession(String authenticationToken) throws NotFoundException, InternalServerException {
        var query = """
                SELECT * FROM sessions
                LEFT JOIN credentials ON sessions.credential_id = credentials.credential_id
                WHERE authentication_token = ?
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper, authenticationToken);

        } catch (EmptyResultDataAccessException exception) {
            logger.warn(exception.getMessage(), exception);
            throw new NotFoundException("Could not find session: '" + authenticationToken + "'");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public SessionView createSession(SessionView sessionView) throws ConflictException, InternalServerException {
        var query = """
                WITH returning_sessions AS (
                    INSERT INTO sessions (
                        credential_id,

                        authentication_token,

                        expiration_date,

                        expiration_time
                    )
                    VALUES (?, ?, ?, ?)
                    RETURNING *
                )
                SELECT *
                FROM returning_sessions
                LEFT JOIN credentials ON returning_sessions.credential_id = credentials.credential_id
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper,

                    sessionView
                            .getCredentialView()
                            .getId(),

                    sessionView.getAuthenticationToken(),

                    sessionView.getExpirationDate(),

                    sessionView.getExpirationTime());

        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConflictException("Session '" + sessionView.getAuthenticationToken() + "' already exists");

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public SessionView updateSession(SessionView sessionView) throws ConflictException, InternalServerException {
        var query = """
                WITH returning_sessions AS (
                    UPDATE sessions SET

                    credential_id = COALESCE(?, credential_id),

                    authentication_token = COALESCE(?, authentication_token),

                    expiration_date = COALESCE(?, expiration_date),

                    expiration_time = COALESCE(?, expiration_time)

                    WHERE session_id = ?
                    RETURNING *
                )
                SELECT *
                FROM returning_sessions
                LEFT JOIN credentials ON returning_sessions.credential_id = credentials.credential_id
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper,

                    sessionView
                            .getCredentialView()
                            .getId(),

                    sessionView.getAuthenticationToken(),

                    sessionView.getExpirationDate(),

                    sessionView.getExpirationTime(),

                    sessionView.getId());

        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConflictException("Session '" + sessionView.getAuthenticationToken() + "' already exists");

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public SessionView deleteSession(SessionView sessionView) throws InternalServerException {
        var query = """
                WITH returning_sessions AS (
                    DELETE FROM sessions WHERE credential_id = ?
                    RETURNING *
                )
                SELECT *
                FROM returning_sessions
                LEFT JOIN credentials ON returning_sessions.credential_id = credentials.credential_id
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper, sessionView.getId());

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

}
