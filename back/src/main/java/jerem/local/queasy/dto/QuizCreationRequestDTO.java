package jerem.local.queasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Request body for quizz creation, containing the quizz informations.")
@Data
@NoArgsConstructor
public class QuizCreationRequestDTO {
    @NotBlank
    private String title;

    private int level;

}
