package jerem.local.queasy.service;

import jerem.local.queasy.dto.AppUserSummaryDTO;
import jerem.local.queasy.dto.RegisterRequestDTO;

/**
 * Service interface for handling user registration.
 * <p>
 * This interface defines the method for registering a new user with the
 * provided registration data.
 * </p>
 */
public interface RegistrationService {
    /**
     * Register a new user provided their informations
     * 
     * @param registerRequestDto
     * @return
     */
    public AppUserSummaryDTO register(RegisterRequestDTO registerRequestDto);
}
