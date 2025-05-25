package jerem.local.queasy.configuration.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jerem.local.queasy.service.JwtService;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // extract the JWT token string from Cookie
            String token = jwtService.extractJwtFromCookie(request);
            log.info("Extracted token jwt from Cookie: " + token);
            if (token != null) {
                log.info("token is not null, decoding...");
                // decode the token string
                Jwt jwt = jwtService.decode(token);
                log.info("Get claims from jwt: " + jwt.getClaims().toString());

                // authenticate using the JWT
                Authentication authentication = jwtService.getAuthentication(jwt);
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
