package jerem.local.queasy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jerem.local.queasy.dto.AppUserDetailedDTO;
import jerem.local.queasy.dto.AppUserSummaryDTO;

import jerem.local.queasy.dto.RegisterRequestDTO;
import jerem.local.queasy.dto.AuthRequestDTO;
import jerem.local.queasy.dto.AuthResponseDTO;

import jerem.local.queasy.service.JwtAuthenticationService;
import jerem.local.queasy.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class used for authentication purpose.
 * <p>
 * This class implements the authentication endpoints like login and register of
 * the application.
 * </p>
 * <p>
 * - {@link JwtAuthenticationService} service that process the user
 * authentication
 * -
 * {@link RegistrationService} service used for registering new users
 * </p>
 * 
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
@Tag(name = "AuthController", description = "Process authentication related operations")
public class AuthController {
    private final JwtAuthenticationService authenticationService;
    private final RegistrationService registrationService;

    public AuthController(JwtAuthenticationService authenticationService,
            RegistrationService registrationService) {
        this.authenticationService = authenticationService;
        this.registrationService = registrationService;

    }

    /**
     * Login to the API.
     * 
     * <p>
     * This method authenticates using POST parameters and return back a Json Web
     * Token.
     * 
     * @param {@link AuthRequestDto} the request DTO.
     * @return {@link AuthResponseDto} the response DTO.
     */

    @Operation(summary = "Login to the API", description = "This endpoint allows a user to authenticate by providing credentials. It returns a JWT token.")
    @ApiResponse(responseCode = "200", description = "Successful authentication, returns a token")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request, HttpServletResponse response) {
        try {
            AuthResponseDTO authResponseDTO = authenticationService.login(request, response);
            return ResponseEntity.ok(authResponseDTO);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(null, request.getIdentifier(), "Authentication failed"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        authenticationService.logout(response);

        return ResponseEntity.ok().build();
    }

    /**
     * Register to the API.
     * 
     * <p>
     * This method register using POST parameters and return back a user Summary.
     * 
     * @param {@link RegisterRequestDto} the request DTO.
     * @return {@link UserSummaryDto} the response DTO.
     */
    @Operation(summary = "Register to the API", description = "This endpoint allows a user to register by providing registerRequest")
    @ApiResponse(responseCode = "200", description = "Successful authentication, returns a token")
    @ApiResponse(responseCode = "409", description = "Email or username already exist")
    @PostMapping("/register")
    public ResponseEntity<AppUserSummaryDTO> register(@RequestBody RegisterRequestDTO request) {
        AppUserSummaryDTO authResponse = registrationService.register(request);
        return ResponseEntity.ok(authResponse);
    }

    /**
     * Retrieve the authenticated user Spring security context
     * 
     * @return {@link AppUserDetailedDTO} the detailed User
     */
    @GetMapping("/me")
    public ResponseEntity<AppUserDetailedDTO> getUserProfile() {

        AppUserDetailedDTO appUserSummaryDTO = authenticationService.getUserProfile();

        return ResponseEntity.ok(appUserSummaryDTO);
    }

    /**
     * Provides the csrfToken given the CSRFToken injected by Spring.
     * (!) Should be used in any request even before login or register endpoint
     *
     * Csrf token is sent in Cookie XSRF-TOKEN
     * You should send csrf token value in X-XSRF-TOKEN header
     * 
     * @param csrfToken injected by Spring with csrf config
     * @return {@link CsrfToken} the token csrf
     */
    @GetMapping("/csrf")
    public ResponseEntity<CsrfToken> getCsrfToken(CsrfToken csrfToken) {
        return ResponseEntity.ok(csrfToken);
    }

}