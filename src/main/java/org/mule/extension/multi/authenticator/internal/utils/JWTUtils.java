package org.mule.extension.multi.authenticator.internal.utils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.*;

import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import java.util.Base64.*;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.mule.extension.multi.authenticator.internal.error.MultiAuthHierarchalErrorEnum;
import org.mule.runtime.extension.api.exception.ModuleException;

/**
 *
 */
public class JWTUtils {

    //Sample method to construct a JWT
    public static String createJWT(String secret, Map<String, Object> claims,
                                   long ttlMillis,
                                   SignatureAlgorithm signatureAlgorithm) {

        // Let's set the JWT Claims.
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims);

        // if it has been specified, let's add the expiration.
        if (ttlMillis >= 0) {
            builder.setExpiration(new Date(System.currentTimeMillis() + ttlMillis));
        }

        signJWT(builder, secret, signatureAlgorithm);

        // Builds the JWT and serializes it to a compact, URL-safe string.
        return builder.compact();
    }

    private static void signJWT(JwtBuilder builder, String secret, SignatureAlgorithm signatureAlgorithm) {
        if (signatureAlgorithm == SignatureAlgorithm.HS256
                || signatureAlgorithm == SignatureAlgorithm.HS384
                || signatureAlgorithm == SignatureAlgorithm.HS512) {

            // We will sign our JWT with our secret.
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            builder.signWith(signatureAlgorithm, signingKey);
        } else if (signatureAlgorithm == SignatureAlgorithm.RS256
                || signatureAlgorithm == SignatureAlgorithm.RS384
                || signatureAlgorithm == SignatureAlgorithm.RS512) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                Decoder b64decoder = Base64.getDecoder();
                byte[] res = b64decoder.decode(secret.getBytes());
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(res);
                RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
                builder.signWith(signatureAlgorithm, privateKey);
            } catch (Exception e) {
                //
            }
        }
    }

    public static String verifyJWT(String secret, String jwt) {

        Claims claims;

        try {
            //This line will throw an exception if it is not a signed JWS (as expected)
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(jwt).getBody();

        } catch (Exception e) {
            throw new ModuleException("Invalid token",
                    MultiAuthHierarchalErrorEnum.INVALID_TOKEN);
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = "";

        try {
            jsonPayload = mapper.writeValueAsString(claims);
        } catch (Exception e) {
            throw new ModuleException("JWT Payload JSON parsing error",
                    MultiAuthHierarchalErrorEnum.JWT_PAYLOAD_JSON_PARSING_ERROR);
        }
        return jsonPayload;
    }

    public static SignatureAlgorithm getSignatureAlgorithm(SignAlgorithmsEnum signAlgorithm){
        switch (signAlgorithm){
            case HS256:
                return SignatureAlgorithm.HS256;
            case HS384:
                return SignatureAlgorithm.HS384;
            case HS512:
                return SignatureAlgorithm.HS512;
            default:
                return SignatureAlgorithm.HS256;
        }
    }
}
