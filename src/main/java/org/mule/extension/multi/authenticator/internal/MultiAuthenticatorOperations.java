package org.mule.extension.multi.authenticator.internal;

import org.mule.extension.multi.authenticator.internal.error.*;
import org.mule.extension.multi.authenticator.internal.utils.*;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.exception.ModuleException;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MultiAuthenticatorOperations {

    /**
     * Operation that Do the HTTP basic authentication.
     *
     * @param authHeader    The authentication header that comes in HTTP request. By default Muleruntime puts this in attributes.headers.authorization.
     * @param hashAlgorithm The selected by the user hash algorithm.
     * @param accessList    A Map that contains Users (keys) and hashed passwords (value).
     * @throws Exception ErrorTypeProvider.
     */
    @MediaType(value = MediaType.ANY, strict = false)
    @Throws(DoHttpBasicAuthErrorsProvider.class)
    public void DoHttpBasicAuthentication(@Optional(defaultValue = "#[attributes.headers.authorization]")
                                          @Alias(value = "AuthenticationHeader",
                                                  description = "Authentication header used to authenticate the service consumer")
                                                  String authHeader,
                                          @Alias(value = "HashAlgorithm",
                                                  description = "Select your favorite hash algorithm")
                                                  HashAlgorithmsEnum hashAlgorithm,
                                          @Alias(value = "AccessList",
                                                  description = "Map that contains the access control list of the service")
                                                  Map<String, String> accessList) throws Exception {
        if (authHeader == null) {
            throw new ModuleException("Missing authentication header.",
                    MultiAuthHierarchalErrorEnum.MISSING_AUTH_HEADER);
        }

        if (hashAlgorithm == null) {
            throw new ModuleException("Missing hash algorithm definition.",
                    MultiAuthHierarchalErrorEnum.MISSING_HASH_ALGORITHM);
        }

        if (accessList == null) {
            throw new ModuleException("Missing access list.",
                    MultiAuthHierarchalErrorEnum.MISSING_ACCESS_LIST);
        }

        // Get the authentication type.
        String authType = authHeader.substring(0, authHeader.indexOf(" "));

        // Auth type has to be "Basic".
        if (authType.toLowerCase().trim().equals("basic")) {

            // Get the base64 complete string.
            String base64UserNPass = authHeader.substring(authHeader.indexOf(" ") + 1);

            // User and pass for decoded Base64.
            String userNPass;

            try {
                // Decode from Base64.
                userNPass = new String(java.util.Base64.getDecoder().decode(base64UserNPass));

            } catch (java.lang.IllegalArgumentException e) {
                // Bad Base64.
                throw new ModuleException("Bad Base64 encode",
                        MultiAuthHierarchalErrorEnum.BAD_BASE64_ENCODE);
            }

            // Get the user and password.
            String user = userNPass.substring(0, userNPass.indexOf(":"));
            String password = userNPass.substring(userNPass.indexOf(":") + 1);

            //Get de user stored hash.
            String userStoredHash = accessList.get(user);

            if (userStoredHash == null) {
                // That user doesn't exist.
                throw new ModuleException("Bad credentials",
                        MultiAuthHierarchalErrorEnum.BAD_CREDENTIALS);
            }

            // Set a flag to store validation.
            Boolean areValidCredentialsFlag = false;

            // Check setup hash algorithm.
            switch (hashAlgorithm) {
                case SHA1:
                    areValidCredentialsFlag = userStoredHash
                            .toLowerCase()
                            .equals(Hasher.hashToSha1(password).toLowerCase());
                    break;
                case SHA256:
                    areValidCredentialsFlag = userStoredHash
                            .toLowerCase()
                            .equals(Hasher.hashToSha256(password).toLowerCase());
                    break;
                case SHA512:
                    areValidCredentialsFlag = userStoredHash
                            .toLowerCase()
                            .equals(Hasher.hashToSha512(password).toLowerCase());
                    break;
            }

            if (!areValidCredentialsFlag) {
                // Hashes did not match.
                throw new ModuleException("Bad credentials",
                        MultiAuthHierarchalErrorEnum.BAD_CREDENTIALS);
            }
        } else {
            // The auth type is not basic.
            throw new ModuleException("Wrong authentication type",
                    MultiAuthHierarchalErrorEnum.INVALID_HTTP_AUTH_TYPE);
        }
    }

    /**
     * Operation that Do an API Key authentication.
     *
     * @param authHeader    The authentication header that you want to use to do the auth.
     * @param hashAlgorithm The selected by the user hash algorithm.
     * @param keys          An array that contains the API Keys.
     * @throws Exception ErrorTypeProvider.
     */
    @MediaType(value = MediaType.ANY, strict = false)
    @Throws(DoApiKeyAuthErrorsProvider.class)
    public void DoApiKeyAuthentication(@Optional(defaultValue = "#[attributes.headers['api-key']]")
                                       @Alias(value = "AuthenticationHeader",
                                               description = "Authentication header used to authenticate the service consumer")
                                               String authHeader,
                                       @Alias(value = "HashAlgorithm",
                                               description = "Select your favorite hash algorithm")
                                               HashAlgorithmsEnum hashAlgorithm,
                                       @Alias(value = "Keys",
                                               description = "Array that contains the keys")
                                               ArrayList<String> keys) throws Exception {
        if (authHeader == null) {
            throw new ModuleException("Missing authentication header.",
                    MultiAuthHierarchalErrorEnum.MISSING_AUTH_HEADER);
        }

        if (hashAlgorithm == null) {
            throw new ModuleException("Missing hash algorithm definition.",
                    MultiAuthHierarchalErrorEnum.MISSING_HASH_ALGORITHM);
        }

        if (keys == null) {
            throw new ModuleException("Missing access list.",
                    MultiAuthHierarchalErrorEnum.MISSING_ACCESS_LIST);
        }

        // Set a flag to store validation.
        Boolean areValidCredentialsFlag = false;

        // Check setup hash algorithm.
        switch (hashAlgorithm) {
            case SHA1:
                areValidCredentialsFlag =
                        ArrayUtils.ArrayListHasHash(Hasher
                                .hashToSha1(authHeader), keys);
                break;
            case SHA256:
                areValidCredentialsFlag =
                        ArrayUtils.ArrayListHasHash(Hasher
                                .hashToSha256(authHeader), keys);
                break;
            case SHA512:
                areValidCredentialsFlag =
                        ArrayUtils.ArrayListHasHash(Hasher
                                .hashToSha512(authHeader), keys);
                break;
        }

        if (!areValidCredentialsFlag) {
            // Hashes did not match.
            throw new ModuleException("Bad credentials",
                    MultiAuthHierarchalErrorEnum.BAD_CREDENTIALS);
        }
    }

    @MediaType(value = MediaType.TEXT_PLAIN, strict = false)
    @Throws(GenerateJWTErrorsProvider.class)
    public String GenerateJsonWebToken(@Alias(value = "Secret",
            description = "JWT generation secret")
                                               String secret,
                                       @Alias(value = "SignatureAlgorithm",
                                               description = "Select your favorite signature algorithm")
                                               SignAlgorithmsEnum signatureAlgorithm,
                                       @Alias(value = "TokenClaims",
                                               description = "The token payload")
                                               Map<String, Object> claims,
                                       @Alias(value = "TokenTtlInMs",
                                               description = "The token time to live in ms")
                                               Long timeTolive) throws Exception {

        if (secret == null) {
            throw new ModuleException("Missing secret",
                    MultiAuthHierarchalErrorEnum.MISSING_SECRET);
        }
        if (timeTolive == null) {
            throw new ModuleException("Missing token TTL",
                    MultiAuthHierarchalErrorEnum.MISSING_TTL);
        }

        return JWTUtils.createJWT(secret,
                claims,
                timeTolive,
                JWTUtils.getSignatureAlgorithm(signatureAlgorithm));

    }

    @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
    @Throws(VerifyJWTErrorsProvider.class)
    public String VerifyJsonWebToken(@Alias(value = "Secret",
                                             description = "JWT generation secret")
                                             String secret,
                                     @Alias(value = "JsonWebToken",
                                             description = "The JWT")
                                             String jwt) throws Exception {

        if (secret == null) {
            throw new ModuleException("Missing secret",
                    MultiAuthHierarchalErrorEnum.MISSING_SECRET);
        }

        if (jwt == null) {
            throw new ModuleException("Missing JWT",
                    MultiAuthHierarchalErrorEnum.MISSING_JWT);
        }

        return JWTUtils.verifyJWT(secret, jwt);
    }
}
