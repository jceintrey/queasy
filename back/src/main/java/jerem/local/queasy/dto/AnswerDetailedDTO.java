package jerem.local.queasy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerDetailedDTO {

    private long id;
    private String text;
    private Boolean correct;
    private Long questionId;
}
