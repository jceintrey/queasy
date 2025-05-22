package jerem.local.queasy.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO containing a detailed Question")
public class QuestionDetailedDTO extends QuestionSummaryDTO {

    private String text;
    private List<AnswerDetailedDTO> answers;

    public QuestionDetailedDTO(String text, List<AnswerDetailedDTO> answers) {
        super();
        this.text = text;
        this.answers = answers;
    }

}
