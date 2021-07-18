package org.mule.extension.multi.authenticator.internal.utils;

import org.mule.extension.multi.authenticator.internal.error.MultiAuthHierarchalErrorEnum;
import org.mule.runtime.extension.api.exception.ModuleException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utils hash static methods collection class.
 */
public class Hasher {

    /**
     * Method used to hash a String using SHA-512.
     * @param input Input String.
     * @return Hashed String.
     */
    public static String hashToSha1(String input) {
        return hashTo(input, "SHA-1", 40);
    }

    /**
     * Method used to hash a String using SHA-256.
     * @param input Input String.
     * @return Hashed String.
     */
    public static String hashToSha256(String input) {
        return hashTo(input, "SHA-256", 64);
    }

    /**
     * Method used to hash a String using SHA-512.
     * @param input Input String.
     * @return Hashed String.
     */
    public static String hashToSha512(String input) {
        return hashTo(input, "SHA-512", 128);
    }

    /**
     * Method used to hash using java security providers.
     * @param input Input String.
     * @param algorithm Algorithm used to hash.
     * @param pad Zero padding.
     * @return Hashed padded string.
     */
    private static String hashTo(String input, String algorithm, Integer pad) {
        try {

            // getInstance() method is called with algorithm.
            MessageDigest md = MessageDigest.getInstance(algorithm);

            // Digest() method is called to calculate message digest of the input string returned as array of byte.
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hash);

            // Convert message digest into hex value.
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros.
            while (hexString.length() < pad) {
                hexString.insert(0, '0');
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // The hash algorithm could not be supported by any security provider for this platform JVM implementation.
            throw new ModuleException("Selected Hash Algorithm is not supported by the platform Java implementation",
                    MultiAuthHierarchalErrorEnum.HASH_ALGORITHM_NOT_SUPPORTED);
        }
    }
}
