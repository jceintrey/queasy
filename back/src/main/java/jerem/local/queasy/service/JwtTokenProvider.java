package jerem.local.queasy.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

/**
 * Service interface responsible for operations on token.
 */
public interface JwtTokenProvider {
    /**
     * Creates a {@link JwtDecoder} used to decode and verify JWT tokens.
     * {@link JwtDecoder} is
     * needed by Spring Security.
     * 
     * @return an instance of {@link JwtDecoder} for decoding JWTs
     */
    JwtDecoder createJwtDecoder();

    /**
     * Creates a {@link JwtEncoder} used to encode and sign JWT tokens.
     *
     * {@link JwtEncoder} is needed by Spring Security.
     * 
     * @return an instance of {@link JwtEncoder} for encoding JWTs
     */
    JwtEncoder createJwtEncoder();

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
}