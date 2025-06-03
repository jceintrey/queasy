package jerem.local.queasy.configuration.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jerem.local.queasy.service.AuthenticationService;
import jerem.local.queasy.service.JwtService;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter used to authenticate the user based on the provided JWT Cookie
 * 
 */
@Slf4j
@Component
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public JwtCookieAuthenticationFilter(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;

    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // extract the JWT token string from Cookie
            String token = jwtService.extractJwtFromCookie(request);
            if (token != null) {
                // decode the token string
                Jwt jwt = jwtService.decode(token);

                // authenticate using the JWT
                Authentication authentication = authenticationService.buildAuthenticationFromJwt(jwt);
                // Authentication authentication = null;
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("JWT authentication succeeded for user: {}", authentication.getName());
            }
        } catch (JwtException e) {
            log.warn("JWT authentication failed: {}", e.getMessage());

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}
