package jerem.local.queasy.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jerem.local.queasy.model.AppUser;
import jerem.local.queasy.model.AppUserDetails;
import jerem.local.queasy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link UserManagementService} responsible for handling
 * user-related operations.
 * <p>
 * This service provides functionalities for user creation, retrieval, and
 * validation. It interacts
 * with the {@link UserRepository} to persist and fetch user data. It uses the
 * {@link PasswordEncoder} to hash the password before storing in the database.
 * </p>
 */
@Service
@Slf4j
public class DefaultUserManagementService implements UserManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a {@code DefaultUserManagementService} with the necessary
     * dependencies.
     *
     * @param userRepository  the repository handling user persistence
     * @param passwordEncoder the encoder for hashing user passwords
     */
    public DefaultUserManagementService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUserDetails createUser(String username, String plainPassword) {

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(plainPassword));

        userRepository.save(user);

        return new AppUserDetails(user);
    }

    @Override
    public boolean isUsernameAlreadyUsed(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Optional<AppUser> getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}