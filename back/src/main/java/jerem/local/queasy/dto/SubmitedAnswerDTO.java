package jerem.local.queasy.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing an answer submited by a playing user.
 * 
 */
@Data
@Schema(description = "Request body containing the answer to submit for a question.")
public class SubmitedAnswerDTO {
    private Long questionId;
    private List<Long> answerIds;

}