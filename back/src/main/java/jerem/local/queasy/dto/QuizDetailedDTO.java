package jerem.local.queasy.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizDetailedDTO extends QuizSummaryDTO {

    private List<QuestionDetailedDTO> questions;
}
