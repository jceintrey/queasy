package jerem.local.queasy.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jerem.local.queasy.dto.QuestionSummaryDTO;
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
        QuizSummaryDTO quizDto = modelMapper.map(quiz, QuizSummaryDTO.class);

        List<QuestionSummaryDTO> questionSummaryDTOs = quiz.getQuestions().stream().map(question -> {
            return questionMapper.toSummaryDto(question);
        }).collect(Collectors.toList());

        quizDto.setQuestions(questionSummaryDTOs);
        return quizDto;
    }

    @Override
    public Quiz toEntity(QuizSummaryDTO quizDto) {
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        return quiz;
    }
}