package jerem.local.queasy.dto;

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

    private String text;
    private Boolean correct;
}
