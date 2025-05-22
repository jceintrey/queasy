package jerem.local.queasy.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jerem.local.queasy.dto.QuestionDetailedDTO;
import jerem.local.queasy.dto.QuestionRequestDTO;
import jerem.local.queasy.dto.QuestionSummaryDTO;
import jerem.local.queasy.model.Question;

/*
 * Mapper class used to convert {@link Question} objects to {@link QuestionDetailedDTO}. It handles
 * conversion in question context.
 */
@Component
public class QuestionMapper implements Mapper<Question, QuestionDetailedDTO> {

    private final AnswerMapper answerMapper;
    private final ModelMapper modelMapper;

    public QuestionMapper(ModelMapper modelMapper, AnswerMapper answerMapper) {
        this.modelMapper = modelMapper;
        this.answerMapper = answerMapper;
    }

    @Override
    public QuestionDetailedDTO toDto(Question question) {
        QuestionDetailedDTO questionDTO = modelMapper.map(question, QuestionDetailedDTO.class);
        questionDTO.setAnswers(this.answerMapper.toDto(question.getAnswers()));
        return questionDTO;
    }

    @Override
    public Question toEntity(QuestionDetailedDTO questionDto) {
        Question question = modelMapper.map(questionDto, Question.class);

        return question;
    }

    public Question toEntity(QuestionRequestDTO questionRequestDTO) {
        return modelMapper.map(questionRequestDTO, Question.class);
    }

    public QuestionSummaryDTO toSummaryDto(Question question) {
        QuestionSummaryDTO questionSummaryDTO = modelMapper.map(question, QuestionSummaryDTO.class);
        return questionSummaryDTO;
    }
}