package jerem.local.queasy.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing minimal information about a
 * quizResult.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response body containing the quizResult.")
public class QuizResultDTO {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("quiz_id")
    private Long quizId;

    private int score;

    @JsonProperty("submited_at")
    private LocalDateTime submittedAt;

}
