package spring.boot.angular.concepts.backend.utils.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spring.boot.angular.concepts.backend.exceptions.InternalServerException;

public class CryptoUtils {

    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    public static String generateSecureRandomBytes(Integer size) throws InternalServerException {
        try {

            // Binary to Hex conversion

            var bytes = new byte[size / 2];

            SecureRandom
                    .getInstance("NativePRNG")
                    .nextBytes(bytes);

            return HexFormat
                    .of()
                    .formatHex(bytes);

        } catch (NoSuchAlgorithmException exception) {
            logger.warn(exception.getMessage(), exception);
            throw new InternalServerException("A invalid algorithm has been used");
        }
    }

    public static String generateSha256Hash(String text) throws InternalServerException {
        try {
            var bytes = text.getBytes("UTF-8");
            var hash = MessageDigest
                    .getInstance("SHA-256")
                    .digest(bytes);

            return HexFormat
                    .of()
                    .formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {
            logger.warn(exception.getMessage(), exception);
            throw new InternalServerException("A invalid algorithm has been used");

        } catch (UnsupportedEncodingException exception) {
            logger.warn(exception.getMessage(), exception);
            throw new InternalServerException("A invalid encoding type has been used");
        }
    }

}
