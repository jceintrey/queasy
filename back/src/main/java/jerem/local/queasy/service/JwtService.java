package jerem.local.queasy.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Service interface responsible for operations on token.
 */
public interface JwtService {
    /**
     * Creates a {@link JwtDecoder} used to decode and verify JWT tokens.
     * {@link JwtDecoder} is
     * needed by Spring Security.
     * 
     * @return an instance of {@link JwtDecoder} for decoding JWTs
     */
    JwtDecoder getJwtDecoder();

    /**
     * Creates a {@link JwtEncoder} used to encode and sign JWT tokens.
     *
     * {@link JwtEncoder} is needed by Spring Security.
     * 
     * @return an instance of {@link JwtEncoder} for encoding JWTs
     */
    JwtEncoder getJwtEncoder();

    /**
     * Generates a JWT token given an authentication.
     * <p>
     * This method takes an {@link Authentication} object and generates a signed JWT
     * token that can
     * be used for authentication and authorization purposes.
     * </p>
     *
     * @param authentication the authentication object containing user details
     * @return a JWT token as a {@code String}
     * @throws Exception if token creation fails due to an encoding issue
     */
    String generateToken(Authentication authentication) throws Exception;

    // Authentication getAuthentication(Jwt jwt);

    /**
     * Extract Jwt String from Jwt Cookie in the given Request
     * 
     * @param request the HttpServletRequest
     * @return token String
     */
    String extractJwtFromCookie(HttpServletRequest request);

    /**
     * Decode the String encoded token
     * 
     * @param token
     * @return the JWT decoded token
     */
    Jwt decode(String token);
}