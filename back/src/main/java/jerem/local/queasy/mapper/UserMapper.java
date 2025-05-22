package jerem.local.queasy.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jerem.local.queasy.dto.AppUserSummaryDTO;
import jerem.local.queasy.dto.RegisterRequestDTO;
import jerem.local.queasy.dto.UserDetailedDTO;
import jerem.local.queasy.model.AppUser;

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
    public AppUserSummaryDTO toDto(AppUser user) {
        AppUserSummaryDTO userDto = modelMapper.map(user, AppUserSummaryDTO.class);
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
