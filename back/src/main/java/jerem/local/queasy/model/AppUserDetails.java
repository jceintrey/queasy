package jerem.local.queasy.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link UserDetails} representing user-specific information.
 * <p>
 * This class stores and provides access to user-related details. It is used by
 * Spring Security for
 * authentication and authorization, as well as by {@code UserManagementService}
 * for user management
 * operations such as account creation, retrieval, and validation.
 * </p>
 */
@Slf4j
public class AppUserDetails implements UserDetails {
    private final AppUser user;

    public AppUserDetails(AppUser user) {
        this.user = user;
    }

    public AppUserDetails(Long id, String username, String password, Set<Role> roles) {
        this.user = new AppUser();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

    }

    /**
     * Retrieves the authorities (roles) granted to the user.
     * <p>
     * Currently, this method returns a default role {@code "ROLE_USER"}. In the
     * future, it should
     * be updated to fetch roles dynamically from the database, ideally using a
     * dedicated roles
     * table.
     * </p>
     * 
     * @return a collection of {@link GrantedAuthority} representing the user's
     *         roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        log.info("Récupération des Authorities"
                + user.getRoles().stream().map(role -> role.getName()).toList().toString());
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Long getId() {
        return user.getId();
    }

    public Set<Role> getRoles() {
        return user.getRoles();
    }

}