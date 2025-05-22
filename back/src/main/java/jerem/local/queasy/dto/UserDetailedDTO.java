package jerem.local.queasy.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) givving detailed user informations.
 * <p>
 * This object contains extends {@link UserSummaryDto} and adds a description
 * and a list of
 * {@QuizResultDTO}.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDetailedDTO extends AppUserSummaryDTO {

    private Long id;

    private List<QuizResultDTO> quizResults;

}