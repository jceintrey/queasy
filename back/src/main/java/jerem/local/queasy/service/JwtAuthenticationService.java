package jerem.local.queasy.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import jakarta.servlet.http.HttpServletResponse;
import jerem.local.queasy.dto.AppUserDetailedDTO;
import jerem.local.queasy.dto.AuthRequestDTO;

/**
 * Service interface responsible for handling user authentication and access to
 * the authenticated
 * user.
 */
public interface AuthenticationService {

    /**
     * Authenticate a user with the provided credentials
     * HttpServletResponse can be used to add header like jwt Cookie
     *
     * @param request  the DTO with user credentials
     * @param response the http response
     * @throws Exception
     */
    public void login(AuthRequestDTO request, HttpServletResponse response) throws Exception;

    /**
     * Logout the authenticated user
     * 
     * @param response
     */
    public void logout(HttpServletResponse response);

    /**
     * Returns the currently authenticated user as a Dto
     *
     * @return the authenticated {@link AppUserDetailedDTO}
     */
    AppUserDetailedDTO getUserProfile();

    /**
     * Build Authentication Object from Jwt
     * 
     * @param jwt
     * @return
     */
    Authentication buildAuthenticationFromJwt(Jwt jwt);

}
