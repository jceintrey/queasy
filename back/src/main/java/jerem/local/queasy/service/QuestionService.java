package jerem.local.queasy.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jerem.local.queasy.dto.AnswerDetailedDTO;
import jerem.local.queasy.dto.AnswerRequestDTO;
import jerem.local.queasy.dto.QuestionDetailedDTO;
import jerem.local.queasy.dto.QuestionRequestDTO;
import jerem.local.queasy.events.QuizModifiedEvent;
import jerem.local.queasy.exception.AnswerNotFoundException;
import jerem.local.queasy.exception.InvalidQuestionException;
import jerem.local.queasy.exception.QuestionNotFoundException;
import jerem.local.queasy.mapper.AnswerMapper;
import jerem.local.queasy.mapper.QuestionMapper;
import jerem.local.queasy.model.Answer;
import jerem.local.queasy.model.Question;
import jerem.local.queasy.model.Quiz;
import jerem.local.queasy.repository.AnswerRepository;
import jerem.local.queasy.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final ApplicationEventPublisher eventPublisher;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository,
            QuestionMapper questionMapper, AnswerMapper answerMapper, ApplicationEventPublisher eventPublisher) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
        this.eventPublisher = eventPublisher;
    }

    public QuestionDetailedDTO getQuestionById(Long id) {
        Question question = getQuestion(id);

        return questionMapper.toDto(question);

    }

    public void deleteQuestion(Question question) {
        // explicit remove of answers, should be although already handled by JPA
        // cascadAll relationnship
        // bewteen Question and Answer
        // question.getAnswers().clear();
        // questionRepository.save(question);

        // delete the question
        log.info("deleteQUestion " + question.getId());
        questionRepository.delete(question);

    }

    public QuestionDetailedDTO addNewQuestion(QuestionRequestDTO questionRequest, Quiz quiz) {
        if (questionRequest.getAnswers() == null || questionRequest.getAnswers().size() < 2)
            throw new InvalidQuestionException("Une question doit être crée avec deux réponses");

        Question question = questionMapper.toEntity(questionRequest);

        question.setQuiz(quiz);
        // Also set inverse relationship
        question.getAnswers().forEach(answer -> answer.setQuestion(question));

        questionRepository.save(question);

        eventPublisher.publishEvent(new QuizModifiedEvent(this, quiz));
        return questionMapper.toDto(question);

    }

    public AnswerDetailedDTO updateQuestionAnswer(Long questionId, Long answerId, AnswerRequestDTO answerRequestDTO) {
        checkAnswerBelongToQuestion(questionId, answerId);
        log.info("Mise à jour de " + answerId);
        Answer answer = getAnswer(answerId);
        log.info("Answer before update: {}", answer.getText());
        log.info("Answer before update: {}", answer.getCorrect());

        log.info(answerRequestDTO.toString());
        if (answerRequestDTO.getText() != null) {
            log.info("Mise à jour du champ test avec {}", answerRequestDTO.getText());
            answer.setText(answerRequestDTO.getText());
        }
        if (answerRequestDTO.getCorrect() != null) {
            log.info("Mise à jour du champ correct avec {}", answerRequestDTO.getCorrect());
            answer.setCorrect(answerRequestDTO.getCorrect());
        }
        answerRepository.save(answer);

        eventPublisher.publishEvent(new QuizModifiedEvent(this, getQuestion(questionId).getQuiz()));

        return answerMapper.toDto(answer);

    }

    public AnswerDetailedDTO addAnswerToQuestion(Long questionId, AnswerRequestDTO answerRequestDTO) {
        Question question = getQuestion(questionId);
        Answer answer = answerMapper.toEntity(answerRequestDTO);

        answer.setQuestion(question);
        question.getAnswers().add(answer);

        answerRepository.save(answer);

        eventPublisher.publishEvent(new QuizModifiedEvent(this, getQuestion(questionId).getQuiz()));

        return answerMapper.toDto(answer);

    }

    private void checkAnswerBelongToQuestion(Long questionId, Long answerId) {
        Question question = getQuestion(questionId);

        Answer answer = getAnswer(answerId);
        if (!question.getAnswers().contains(answer))
            throw new InvalidQuestionException(
                    "Answer with id " + answerId + " does not belong to question with Id " + questionId);

    }

    public QuestionDetailedDTO removeAnswerFromQuestion(Long questionId, Long answerId) {
        checkAnswerBelongToQuestion(questionId, answerId);

        Question question = getQuestion(questionId);
        Answer answer = getAnswer(answerId);

        question.getAnswers().remove(answer);
        answerRepository.delete(answer);

        eventPublisher.publishEvent(new QuizModifiedEvent(this, getQuestion(questionId).getQuiz()));

        return questionMapper.toDto(question);

    }

    private Question getQuestion(Long id) {
        Question question = this.questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id " + id,
                        "QuestionService.getQuestionById"));
        return question;
    }

    private Answer getAnswer(Long id) {
        Answer answer = this.answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id " + id));
        ;
        return answer;

    }

}
