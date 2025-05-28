package jerem.local.queasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) representing minimal information about a user.
 * 
 */
@Data
@Schema(description = "DTO containing the user informations.")
@NoArgsConstructor
public class AppUserSummaryDTO {
    private Long id;

    public AppUserSummaryDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @NonNull
    @Size(max = 30)
    private String username;

    @Email
    @Schema(description = "User email")
    private String email;

}