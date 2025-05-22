package jerem.local.queasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing an anthentication request.
 * 
 * <p>
 * This class is used to transfer lightweight anthentication information such as
 * indentifier and
 * password, as part of a request payload in the context of authentication login
 * endpoint.
 */
@Schema(description = "Request body for user login, containing credentials.")
@Data
@NoArgsConstructor
public class AuthRequestDTO {
    @Schema(description = "The identifier can be an email or the username.", example = "jdoe")
    private String identifier;
    private String password;

    public AuthRequestDTO(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}