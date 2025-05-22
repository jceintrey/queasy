package jerem.local.queasy.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jerem.local.queasy.dto.AppUserSummaryDTO;
import jerem.local.queasy.dto.RegisterRequestDTO;
import jerem.local.queasy.exception.UsernameAlreadyExistException;
import jerem.local.queasy.mapper.UserMapper;
import jerem.local.queasy.model.AppUser;
import jerem.local.queasy.repository.UserRepository;

/**
 * Default implementation of {@link RegistrationService} that implements the
 * register method.
 */
@Service
public class DefaultRegistrationService implements RegistrationService {
    private final UserManagementService userManagementService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DefaultRegistrationService(UserManagementService userManagementService,
            UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository,
            ModelMapper modelMapper) {
        this.userManagementService = userManagementService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public AppUserSummaryDTO register(RegisterRequestDTO registerRequestDto) {

        if (userManagementService.isUsernameAlreadyUsed(registerRequestDto.getUsername())) {
            throw new UsernameAlreadyExistException("Username already used",
                    "RegistrationService.register");
        }

        registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        AppUser user = userMapper.toUserFromRegisterRequestDto(registerRequestDto);
        userRepository.save(user);
        return userMapper.toDto(user);

    }
}