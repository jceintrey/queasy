package jerem.local.queasy.service;

import jerem.local.queasy.dto.AuthRequestDTO;
import jerem.local.queasy.dto.AuthResponseDTO;
import jerem.local.queasy.model.AppUser;

/**
 * Service interface responsible for handling user authentication and access to
 * the authenticated
 * user.
 */
public interface AuthenticationService {

    /**
     * Authenticates a user based on the provided login credentials.
     * <p>
     * This method uses
     * {@link org.springframework.security.authentication.AuthenticationManager} to
     * verify the user's identity and generate a JWT token using {@code jwtFactory}.
     * </p>
     *
     * @param request the login request containing the user's identifier and
     *                password
     * @return an {@link AuthResponseDto} containing the generated authentication
     *         response
     * @throws Exception if authentication fails
     */
    public AuthResponseDTO authenticate(AuthRequestDTO request) throws Exception;

    /**
     * Returns the currently authenticated user.
     *
     * @return the authenticated {@link User}
     */
    public AppUser getAuthenticatedUser();

}
