package jerem.local.queasy.service;

import java.time.Duration;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jerem.local.queasy.dto.AppUserDetailedDTO;
import jerem.local.queasy.dto.AppUserSummaryDTO;
import jerem.local.queasy.dto.AuthRequestDTO;
import jerem.local.queasy.exception.UserNotAuthenticatedException;
import jerem.local.queasy.exception.UserNotFoundException;
import jerem.local.queasy.mapper.UserMapper;
import jerem.local.queasy.model.AppUser;
import jerem.local.queasy.model.AppUserDetails;
import jerem.local.queasy.model.Role;
import jerem.local.queasy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link JwtAuthenticationService}.
 * <p>
 * Handles user authentication using Spring Security's
 * {@link AuthenticationManager} and generates a
 * JWT using {@link JwtService}.
 * The JWT is provided into a http only cookie
 * </p>
 */
@Slf4j
@Service
@Primary
public class JwtHttpOnlyAuthenticationService implements JwtAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final static String JWT_COOKIE_NAME = "jwt";

    public JwtHttpOnlyAuthenticationService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;

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

            String token = jwtService.generateToken(authentication);

            ResponseCookie cookie = ResponseCookie.from(JWT_COOKIE_NAME, token)
                    .httpOnly(true)
                    .secure(false) // true if https and false otherwise
                    .path("/")
                    .maxAge(Duration.ofHours(2))
                    .sameSite("Lax")
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            throw new AuthenticationServiceException("Authentication failed");
        }

    }

    @Override
    public void logout(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        SecurityContextHolder.clearContext();
    }

    @Override
    public AppUserDetailedDTO getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("User is not authenticated");
            throw new UserNotAuthenticatedException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof AppUserDetails)) {
            String principalType = (principal != null) ? principal.getClass().getName() : "null";
            log.error("Auth principal is not an AppUserDetails object but: {}", principalType);
            throw new UserNotFoundException(
                    "User not found because principal is not an AppUserDetails --> " + principalType);
        }

        AppUserDetails appUserDetails = (AppUserDetails) principal;
        long userId = appUserDetails.getId();
        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        return userMapper.toDto(appUser);

    }

    @Override
    public Authentication buildAuthenticationFromJwt(Jwt jwt) {

        Long userId = jwt.getClaim("id");

        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotAuthenticatedException("User not found with id " + userId));

        UserDetails userDetails = new AppUserDetails(appUser);

        log.debug("User id is :" + userId);
        log.debug("User roles: {}", appUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", ")));

        return new UsernamePasswordAuthenticationToken(userDetails, jwt.getTokenValue(), userDetails.getAuthorities());

    }

}