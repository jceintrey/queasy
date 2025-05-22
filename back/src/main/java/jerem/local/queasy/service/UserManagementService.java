package jerem.local.queasy.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import jerem.local.queasy.model.AppUser;

/**
 * Service interface for managing users. It defines methods for create or
 * retrieve informations.
 * <p>
 * This service defines the contract for managing users.
 * 
 * </p>
 * 
 * @see DefaultMessageService for the default implementation.
 */
public interface UserManagementService {

    /**
     * Creates a new user with the given details and returns a UserDetails object.
     * This method is
     * responsible for creating a new user in the application.
     * 
     * @param plainPassword the plain-text password of the new user
     * @param username      the name of the new user.
     * @return a UserDetails object that represents the created user.
     */
    public UserDetails createUser(String username, String plainPassword);

    /**
     * Return true if the username is already present in the Database.
     * 
     * @param {@link String} username of the user
     * @return a {@link boolean} true if the username already exist, and false
     *         otherwise
     */
    public boolean isUsernameAlreadyUsed(String username);

    /**
     * Retrieves the {@link UserEntity} by their Username.
     * 
     * This method is intented to be used by other services and not by controllers
     * themselves in
     * order to respect the layer model.
     * 
     * @param {@link String} username of the user
     * @return an {@link Optional} containing the {@link UserEntity} if found,
     *         otherwise empty.
     */
    public Optional<AppUser> getUserEntityByUsername(String username);

}