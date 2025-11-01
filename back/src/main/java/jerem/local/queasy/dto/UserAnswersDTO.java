package jerem.local.queasy.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the answers submited by a playing
 * user.
 * 
 */
@Data
@Schema(description = "Request body containing the answers of the quiz to submit.")
public class UserAnswersDTO {

    private List<SubmitedAnswerDTO> answers;

}
