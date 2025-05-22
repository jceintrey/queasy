package jerem.local.queasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) used as request to create an answer.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerRequestDTO {
    @Schema(description = "Text of the answer", example = "Paris")
    private String text;
    @Schema(description = "Whether the answer is correct", example = "true")
    private Boolean correct;
}
