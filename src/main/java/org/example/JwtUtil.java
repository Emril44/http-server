package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Base64;

public class JwtUtil {
    private static final String SECRET_KEY = "SEVENFOOTRATSRATSALONGHISRATSWHENHESEESYOURRATSITALLFADESTORATS";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(String username) {
        try {
            // The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            // We will sign our JWT with our ApiKey secret
            byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET_KEY);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            // Set the JWT Claims
            JwtBuilder builder = Jwts.builder().setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(signatureAlgorithm, signingKey);

            // Builds the JWT and serializes it to a compact, URL-safe string
            return builder.compact();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating token", e);
        }
    }

    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJwt(token)
                .getBody();
    }

    public static boolean validateToken(String token, String username) {
        final String tokenUsername = extractClaims(token).getSubject();
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    private static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
