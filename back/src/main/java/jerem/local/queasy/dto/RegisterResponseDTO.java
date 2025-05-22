package jerem.local.queasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) returned after successfull register.
 * <p>
 * 
 */
@Schema(description = "Response body for user register")
@Data
@NoArgsConstructor
public class RegisterResponseDTO {
    @NotBlank
    private String token;

}