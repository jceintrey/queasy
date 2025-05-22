package jerem.local.queasy.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import jerem.local.queasy.dto.AuthRequestDTO;
import jerem.local.queasy.dto.AuthResponseDTO;
import jerem.local.queasy.exception.UserNotFoundException;
import jerem.local.queasy.model.AppUser;
import jerem.local.queasy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link AuthenticationService}.
 * <p>
 * Handles user authentication using Spring Security's
 * {@link AuthenticationManager} and generates a
 * JWT using {@link JwtTokenProvider}. Also provides access to the currently
 * authenticated
 * {@link User}.
 * </p>
 */
@Slf4j
@Service
@Primary
public class DefaultAuthenticationService implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public DefaultAuthenticationService(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;

    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) throws Exception {
        AppUser user = userRepository.findByUsername(request.getIdentifier())
                .orElseGet(() -> userRepository.findByEmail(request.getIdentifier()).orElseThrow(
                        () -> new UserNotFoundException("Utilisateur non trouvé")));

        try {
            log.info(request.getIdentifier() + " " + request.getPassword());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), request.getPassword()));

            String token = jwtTokenProvider.generateToken(authentication);
            log.info("token: " + token);
            return new AuthResponseDTO(token);

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

}