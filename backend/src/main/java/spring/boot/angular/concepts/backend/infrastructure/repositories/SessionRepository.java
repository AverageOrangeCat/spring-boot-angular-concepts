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
        var sessionView = new SessionView();

        sessionView.setId(resultSet.getLong("session_id"));
        sessionView.setSessionToken(resultSet.getString("session_token"));
        sessionView.setExpirationUnixDate(resultSet.getLong("expiration_unix_date"));

        return sessionView;
    };

    public SessionView getSession(String authenticationToken) throws NotFoundException, InternalServerException {
        var query = """
                SELECT * FROM sessions 
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
                INSERT INTO sessions (
                    authentication_token,
                    expiration_unix_date
                )
                VALUES (?, ?)
                RETURNING *
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper,
                    sessionView.getSessionToken(),
                    sessionView.getExpirationUnixDate());

        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConflictException("Session '" + sessionView.getSessionToken() + "' already exists");

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
                UPDATE sessions SET

                authentication_token = COALESCE(?, authentication_token),
                expiration_unix_date = COALESCE(?, expiration_unix_date)

                WHERE session_id = ?
                RETURNING *
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper,
                    sessionView.getSessionToken(),
                    sessionView.getExpirationUnixDate());

        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConflictException("Session '" + sessionView.getSessionToken() + "' already exists");

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
                DELETE FROM sessions WHERE credential_id = ?
                RETURNING *
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
