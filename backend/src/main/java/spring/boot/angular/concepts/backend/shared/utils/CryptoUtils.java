package spring.boot.angular.concepts.backend.shared.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.boot.angular.concepts.backend.shared.exceptions.InternalServerException;

@Component
public class CryptoUtils {

    private final HexFormat hexFormat = HexFormat.of();

    private final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    public String generateSecureRandomBytes(Integer size) throws InternalServerException {
        try {

            // Binary to Hex conversion

            var bytes = new byte[size / 2];

            SecureRandom
                    .getInstance("NativePRNGBlocking")
                    .nextBytes(bytes);

            return hexFormat.formatHex(bytes);

        } catch (NoSuchAlgorithmException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("A invalid algorithm has been used");
        }
    }

    public String generateSha256Hash(String text) throws InternalServerException {
        try {
            var bytes = text.getBytes(StandardCharsets.UTF_8);
            var hash = MessageDigest
                    .getInstance("SHA3-256")
                    .digest(bytes);

            return hexFormat.formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("A invalid algorithm has been used");
        }
    }

    public String generateSha384Hash(String text) throws InternalServerException {
        try {
            var bytes = text.getBytes(StandardCharsets.UTF_8);
            var hash = MessageDigest
                    .getInstance("SHA3-384")
                    .digest(bytes);

            return hexFormat.formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("A invalid algorithm has been used");
        }
    }

    public String generateSha512Hash(String text) throws InternalServerException {
        try {
            var bytes = text.getBytes(StandardCharsets.UTF_8);
            var hash = MessageDigest
                    .getInstance("SHA3-512")
                    .digest(bytes);

            return hexFormat.formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {
            logger.error(exception.getMessage(), exception);
            throw new InternalServerException("A invalid algorithm has been used");
        }
    }

}
