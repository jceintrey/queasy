package jerem.local.queasy.service;

import java.time.Duration;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jerem.local.queasy.dto.AuthRequestDTO;
import jerem.local.queasy.exception.UserNotFoundException;
import jerem.local.queasy.model.AppUser;
import jerem.local.queasy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link AuthenticationService}.
 * <p>
 * Handles user authentication using Spring Security's
 * {@link AuthenticationManager} and generates a
 * JWT using {@link JwtService}. Also provides access to the currently
 * authenticated
 * {@link User}.
 * </p>
 */
@Slf4j
@Service
@Primary
public class DefaultAuthenticationService implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtTokenProvider;
    private final UserRepository userRepository;

    public DefaultAuthenticationService(
            AuthenticationManager authenticationManager,
            JwtService jwtTokenProvider,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;

    }

    public void login(AuthRequestDTO request, HttpServletResponse response) throws Exception {
        AppUser user = userRepository.findByUsername(request.getIdentifier())
                .orElseGet(() -> userRepository.findByEmail(request.getIdentifier()).orElseThrow(
                        () -> new UserNotFoundException("Utilisateur non trouvé")));

        try {
            log.info(request.getIdentifier() + " " + request.getPassword());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), request.getPassword()));

            log.info("DefaultauthenticationService.authenticate: is authenticated ? "
                    + authentication.isAuthenticated());

            String token = jwtTokenProvider.generateToken(authentication);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(false) // true if https and false otherwise
                    .path("/")
                    .maxAge(Duration.ofHours(2))
                    .sameSite("Lax") // ou Strict / None (si tu fais du cross-site avec credentials)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            throw new AuthenticationServiceException("Authentication failed");
        }

    }

    @Override
    public AppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof Jwt) {
                Jwt jwt = (Jwt) principal;
                Long userId = jwt.getClaim("id");
                log.debug("" + jwt.getClaim("id"));
                log.debug(jwt.getClaim("username"));
                log.debug(jwt.getClaim("email"));

                return userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
            }
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public void logout(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // expire immédiatement
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        SecurityContextHolder.clearContext();
    }

}