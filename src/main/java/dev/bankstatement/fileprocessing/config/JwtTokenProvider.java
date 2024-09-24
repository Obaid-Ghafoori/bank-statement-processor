package dev.bankstatement.fileprocessing.config;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Provides functionality for generating, resolving, and validating JSON Web Tokens (JWTs) used for authentication.
 * 
 * @author Obaid
 */
@Component
public class JwtTokenProvider {
    private final String secretKey = System.getenv("SECRET_KEY ");
    private final long validityInMilliseconds = 3600000; // 1h

    /**
     * Generates a new JWT token based on the provided username and roles.
     * 
     * @param username the username to associate with the token
     * @param roles the roles to associate with the token
     * @return a new JWT token as a string
     */
    public String createToken(String username, List<String> roles) {
        var claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);


        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Extracts the JWT token from the Authorization header of the provided HTTP request.
     * 
     * @param request the HTTP request to extract the token from
     * @return the extracted JWT token, or null if not found
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        return bearerToken != null && bearerToken.startsWith("Bearer ")
                ? bearerToken.substring(7)
                : null;
    }

    /**
     * Validates a JWT token by checking its signature and expiration date.
     * 
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     * @throws IllegalArgumentException if the token is invalid
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }
}