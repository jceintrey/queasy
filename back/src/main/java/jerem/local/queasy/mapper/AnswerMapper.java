package jerem.local.queasy.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jerem.local.queasy.dto.AnswerDetailedDTO;
import jerem.local.queasy.dto.AnswerRequestDTO;
import jerem.local.queasy.exception.QuestionNotFoundException;
import jerem.local.queasy.model.Answer;
import jerem.local.queasy.model.Question;
import jerem.local.queasy.repository.QuestionRepository;

/*
 * Mapper class used to convert {@link Answer} objects to {@link AnswerDetailedDTO}. It handles
 * conversion in answer context.
 */
@Component
public class AnswerMapper implements Mapper<Answer, AnswerDetailedDTO> {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public AnswerMapper(ModelMapper modelMapper, QuestionRepository questionRepository) {
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public AnswerDetailedDTO toDto(Answer answer) {
        AnswerDetailedDTO answerDetailedDTO = modelMapper.map(answer, AnswerDetailedDTO.class);
        answerDetailedDTO.setQuestionId(answer.getQuestion().getId());
        return answerDetailedDTO;
    }

    @Override
    public Answer toEntity(AnswerDetailedDTO answerDetailedDTO) {
        Answer answer = modelMapper.map(answerDetailedDTO, Answer.class);
        if (answerDetailedDTO.getQuestionId() != null) {
            Question question = questionRepository.findById(answerDetailedDTO.getQuestionId()).orElseThrow(
                    () -> new QuestionNotFoundException(
                            "Question not found with id " + answerDetailedDTO.getQuestionId(),
                            "AnswerMapper.toEntity"));
            answer.setQuestion(question);
        }
        return answer;
    }

    public Answer toEntity(AnswerRequestDTO answerRequestDTO) {
        Answer answer = modelMapper.map(answerRequestDTO, Answer.class);
        return answer;
    }
}