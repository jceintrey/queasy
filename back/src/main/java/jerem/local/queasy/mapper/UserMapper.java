package jerem.local.queasy.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jerem.local.queasy.dto.AppUserDetailedDTO;
import jerem.local.queasy.dto.AppUserSummaryDTO;
import jerem.local.queasy.dto.RegisterRequestDTO;
import jerem.local.queasy.dto.UserDetailedDTO;
import jerem.local.queasy.model.AppUser;
import jerem.local.queasy.model.Role;

/*
 * Mapper class used to convert {@link User} objects to {@link UserSummaryDto}. It handles
 * conversion in registration context, and in userProfile operations context.
 */
@Component
public class UserMapper implements Mapper<AppUser, AppUserSummaryDTO> {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AppUserDetailedDTO toDto(AppUser user) {
        AppUserDetailedDTO userDto = modelMapper.map(user, AppUserDetailedDTO.class);
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        userDto.setRoles(roles);
        return userDto;
    }

    @Override
    public AppUser toEntity(AppUserSummaryDTO userDto) {
        AppUser user = modelMapper.map(userDto, AppUser.class);
        return user;
    }

    public AppUser toUserFromUserProfileDto(UserDetailedDTO userProfileDto) {
        AppUser user = modelMapper.map(userProfileDto, AppUser.class);

        return user;
    }

    public AppUser toUserFromRegisterRequestDto(RegisterRequestDTO registerRequestDto) {
        AppUser user = modelMapper.map(registerRequestDto, AppUser.class);

        return user;
    }

}
