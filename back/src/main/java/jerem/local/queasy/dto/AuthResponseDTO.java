package jerem.local.queasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing an anthentication response.
 * 
 * <p>
 * This class is used to transfer lightweight anthentication information with
 * token, as part of a
 * response payload in the context of authentication login endpoint.
 */
@Schema(description = "Response body containing the JSON Web Token used for API authentication.")
@Data
@AllArgsConstructor
public class AuthResponseDTO {
    @Schema(description = "The JSON Web Token used for authentication.", example = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYm9iQG1haWwudGxkIiwiZXhwIjoxNzM4MzQzMTk1LCJpYXQiOjE3MzgzMzk1OTUsInJvbGVzIjoiVVNFUiJ9.dZccscSkZYawnt40cVRFF-3ds5BO7p5yhMH_Syuofrs")
    private String token;

    private String username;

    private String description;

}