package jerem.local.queasy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jerem.local.queasy.model.AppUser;
import jerem.local.queasy.model.AppUserDetails;
import jerem.local.queasy.repository.UserRepository;

/**
 * Default implementation of {@link UserDetailsService}.
 * <p>
 * This service loads user details from the {@link UserRepository} by their
 * email address and
 * returns a {@link UserDetails} object for authentication in Spring Security.
 * </p>
 */
@Service
@AllArgsConstructor
@Slf4j
@Primary
public class DefaultUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    /**
     * Loads a user from the database by their email and returns a Spring Security
     * {@link UserDetails} object.
     * <p>
     * This method is called by Spring Security during the authentication process.
     * </p>
     * 
     * @param email the email of the user to load
     * @return a {@link UserDetails} object representing the authenticated user
     * @throws UsernameNotFoundException if no user with the given email is found in
     *                                   the repository
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username: " + username + "not found"));

        return new AppUserDetails(user);

    }

}