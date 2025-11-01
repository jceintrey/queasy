package jerem.local.queasy.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jerem.local.queasy.dto.QuestionDetailedDTO;
import jerem.local.queasy.dto.QuestionSummaryDTO;
import jerem.local.queasy.dto.QuizDetailedDTO;
import jerem.local.queasy.dto.QuizSummaryDTO;
import jerem.local.queasy.model.Quiz;

/*
 * Mapper class used to convert {@link Quiz} objects to {@link QuizSummaryDTO}. It handles
 * conversion in quiz context.
 */
@Component
public class QuizMapper implements Mapper<Quiz, QuizSummaryDTO> {

    private final QuestionMapper questionMapper;
    private final ModelMapper modelMapper;

    public QuizMapper(ModelMapper modelMapper, QuestionMapper questionMapper) {
        this.modelMapper = modelMapper;
        this.questionMapper = questionMapper;
    }

    @Override
    public QuizSummaryDTO toDto(Quiz quiz) {
        QuizDetailedDTO quizDto = modelMapper.map(quiz, QuizDetailedDTO.class);

        List<QuestionDetailedDTO> questionDetailedDTOs = quiz.getQuestions().stream().map(question -> {
            return questionMapper.toDto(question);
        }).collect(Collectors.toList());

        quizDto.setQuestions(questionDetailedDTOs);
        quizDto.setNumberOfQuestions(questionDetailedDTOs.size());

        return quizDto;
    }

    @Override
    public Quiz toEntity(QuizSummaryDTO quizDto) {
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        return quiz;
    }
}