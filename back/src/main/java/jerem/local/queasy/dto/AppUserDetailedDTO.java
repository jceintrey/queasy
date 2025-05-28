package jerem.local.queasy.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDetailedDTO extends AppUserSummaryDTO {

    private List<String> roles;

    public AppUserDetailedDTO(String username, String email, List<String> roles) {
        super(username, email);
        this.roles = roles;

    }

}
