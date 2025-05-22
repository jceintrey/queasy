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
public class QuestionRequestDTO extends QuestionSummaryDTO {

    private String text;
    private List<AnswerRequestDTO> answers;

    public QuestionRequestDTO(String text, List<AnswerRequestDTO> answers) {
        super();
        this.text = text;
        this.answers = answers;
    }

}
