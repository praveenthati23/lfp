package com.lastfarewells.backend.utils;

import com.lastfarewells.backend.exception.IAMException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.text.ParseException;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {


    private static String secretKey;

    public static void setSecretKey(String key) {
        secretKey = key;
    }

    private JWTUtils() {
    }

    public static SignedJWT decodeJWT(String token) {
        try {
            var decodedJWT = SignedJWT  // or PlainJWT or EncryptedJWT
                .parse(token);
            return decodedJWT;
        } catch (ParseException e) {
            throw new IAMException("Invalid token!!");
        }
    }

    public static String generateVerificationToken(String iamId) {
        return Jwts.builder()
            .setSubject(iamId)
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public static String getEmailFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload().getSubject();
    }

    public static boolean verifyToken(String token) {
        try {
            // Parse the JWT token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Get the payload
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            Date expirationTime = claimsSet.getExpirationTime();
            return expirationTime.after(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

}
