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
import spring.boot.angular.concepts.backend.shared.exceptions.ConflictException;
import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;
import spring.boot.angular.concepts.backend.shared.exceptions.NotFoundException;

@Repository
public class CredentialRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(CredentialRepository.class);

    private final RowMapper<CredentialView> rowMapper = (resultSet, rowNumber) -> {
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

        return credentialView;
    };

    public CredentialView getCredential(String email) throws NotFoundException, InternalServerException {
        var query = "SELECT * FROM credentials WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(query, rowMapper, email);

        } catch (EmptyResultDataAccessException exception) {
            logger.warn(exception.getMessage(), exception);
            throw new NotFoundException("Could not find credential: '" + email + "'");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public CredentialView createCredential(CredentialView credentialView)
            throws ConflictException, InternalServerException {
        var query = """
                INSERT INTO credentials (
                    email,

                    password_salt,

                    password_hash,

                    first_name,

                    last_name,

                    birth_date,

                    address,

                    house_number,

                    postal_code,

                    city,

                    country
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING *
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper,

                    credentialView.getEmail(),

                    credentialView.getPasswordSalt(),

                    credentialView.getPasswordHash(),

                    credentialView.getFirstName(),

                    credentialView.getLastName(),

                    credentialView.getBirthDate(),

                    credentialView.getAddress(),

                    credentialView.getHouseNumber(),

                    credentialView.getPostalCode(),

                    credentialView.getCity(),

                    credentialView.getCountry());

        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConflictException("Credential '" + credentialView.getEmail() + "' already exists");

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public CredentialView updateCredential(CredentialView credentialView)
            throws ConflictException, InternalServerException {
        var query = """
                UPDATE credentials SET

                email = COALESCE(?, email),

                password_salt = COALESCE(?, password_salt),

                password_hash = COALESCE(?, password_hash),

                first_name = COALESCE(?, first_name),

                last_name = COALESCE(?, last_name),

                birth_date = COALESCE(?, birth_date),

                address = COALESCE(?, address),

                house_number = COALESCE(?, house_number),

                postal_code = COALESCE(?, postal_code),

                city = COALESCE(?, city),

                country = COALESCE(?, country)

                WHERE credential_id = ?
                RETURNING *
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper,

                    credentialView.getEmail(),

                    credentialView.getPasswordSalt(),

                    credentialView.getPasswordHash(),

                    credentialView.getFirstName(),

                    credentialView.getLastName(),

                    credentialView.getBirthDate(),

                    credentialView.getAddress(),

                    credentialView.getHouseNumber(),

                    credentialView.getPostalCode(),

                    credentialView.getCity(),

                    credentialView.getCountry(),

                    credentialView.getId());

        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConflictException("Credential '" + credentialView.getEmail() + "' already exists");

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

    public CredentialView deleteCredential(CredentialView credentialView) throws InternalServerException {
        var query = """
                DELETE FROM credentials WHERE credential_id = ?
                RETURNING *
                """;

        try {
            return jdbcTemplate.queryForObject(query, rowMapper, credentialView.getId());

        } catch (EmptyResultDataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");

        } catch (DataAccessException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("Something went wrong during the database processing");
        }
    }

}
