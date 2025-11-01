package jerem.local.queasy.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing minimal information regarding quiz.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response body containing the quiz.")
public class QuizSummaryDTO {
    private Long id;

    private String title;

    private boolean valid;

    @JsonProperty("validation_message")
    private String validationMessage;

    private int numberOfQuestions;

    private int level;
}