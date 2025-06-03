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

import java.util.Optional;

/**
 * Service responsible for managing questions and answers
 * within a quiz.
 */
@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor-based dependency injection.
     */
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository,
            QuestionMapper questionMapper, AnswerMapper answerMapper,
            ApplicationEventPublisher eventPublisher) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Retrieves a question by its ID and returns its detailed DTO.
     *
     * @param id The ID of the question.
     * @return Detailed representation of the question.
     * @throws QuestionNotFoundException if the question is not found.
     */
    public QuestionDetailedDTO getQuestionById(Long id) {
        Question question = getQuestion(id);
        return questionMapper.toDto(question);
    }

    /**
     * Deletes a given question from the database.
     *
     * @param question The question entity to delete.
     */
    public void deleteQuestion(Question question) {
        log.info("deleteQuestion " + question.getId());
        questionRepository.delete(question);
    }

    /**
     * Adds a new question to a quiz.
     *
     * @param questionRequest DTO containing question data.
     * @param quiz            The quiz to which the question is added.
     * @return The created question as a detailed DTO.
     * @throws InvalidQuestionException if the question has less than 2 answers.
     */
    public QuestionDetailedDTO addNewQuestion(QuestionRequestDTO questionRequest, Quiz quiz) {
        if (questionRequest.getAnswers() == null || questionRequest.getAnswers().size() < 2)
            throw new InvalidQuestionException("A question must be created with at least two answers");

        Question question = questionMapper.toEntity(questionRequest);
        question.setQuiz(quiz);
        question.getAnswers().forEach(answer -> answer.setQuestion(question));

        questionRepository.save(question);
        eventPublisher.publishEvent(new QuizModifiedEvent(this, quiz));

        return questionMapper.toDto(question);
    }

    /**
     * Updates an existing answer associated with a question.
     *
     * @param questionId       ID of the question.
     * @param answerId         ID of the answer to update.
     * @param answerRequestDTO Updated answer data.
     * @return Updated answer as a detailed DTO.
     * @throws InvalidQuestionException if the answer does not belong to the
     *                                  question.
     */
    public AnswerDetailedDTO updateQuestionAnswer(Long questionId, Long answerId, AnswerRequestDTO answerRequestDTO) {
        checkAnswerBelongToQuestion(questionId, answerId);
        log.info("Updating answer with ID " + answerId);
        Answer answer = getAnswer(answerId);

        if (answerRequestDTO.getText() != null) {
            answer.setText(answerRequestDTO.getText());
        }
        if (answerRequestDTO.getCorrect() != null) {
            answer.setCorrect(answerRequestDTO.getCorrect());
        }

        answerRepository.save(answer);
        eventPublisher.publishEvent(new QuizModifiedEvent(this, getQuestion(questionId).getQuiz()));
        return answerMapper.toDto(answer);
    }

    /**
     * Adds a new answer to a given question.
     *
     * @param questionId       ID of the question.
     * @param answerRequestDTO DTO containing new answer data.
     * @return The created answer as a detailed DTO.
     */
    public AnswerDetailedDTO addAnswerToQuestion(Long questionId, AnswerRequestDTO answerRequestDTO) {
        Question question = getQuestion(questionId);
        Answer answer = answerMapper.toEntity(answerRequestDTO);

        answer.setQuestion(question);
        question.getAnswers().add(answer);

        answerRepository.save(answer);
        eventPublisher.publishEvent(new QuizModifiedEvent(this, getQuestion(questionId).getQuiz()));

        return answerMapper.toDto(answer);
    }

    /**
     * Removes an answer from a question.
     *
     * @param questionId ID of the question.
     * @param answerId   ID of the answer to remove.
     * @return Updated question as a detailed DTO.
     * @throws InvalidQuestionException if the answer does not belong to the
     *                                  question.
     */
    public QuestionDetailedDTO removeAnswerFromQuestion(Long questionId, Long answerId) {
        checkAnswerBelongToQuestion(questionId, answerId);

        Question question = getQuestion(questionId);
        Answer answer = getAnswer(answerId);

        question.getAnswers().remove(answer);
        answerRepository.delete(answer);

        eventPublisher.publishEvent(new QuizModifiedEvent(this, getQuestion(questionId).getQuiz()));

        return questionMapper.toDto(question);
    }

    /**
     * Checks that an answer belongs to a given question.
     *
     * @param questionId ID of the question.
     * @param answerId   ID of the answer.
     * @throws InvalidQuestionException if the answer is not linked to the question.
     */
    private void checkAnswerBelongToQuestion(Long questionId, Long answerId) {
        Question question = getQuestion(questionId);
        Answer answer = getAnswer(answerId);

        if (!question.getAnswers().contains(answer))
            throw new InvalidQuestionException(
                    "Answer with ID " + answerId + " does not belong to question with ID " + questionId);
    }

    /**
     * Retrieves a Question entity by its ID.
     *
     * @param id The ID of the question.
     * @return The Question entity.
     * @throws QuestionNotFoundException if not found.
     */
    private Question getQuestion(Long id) {
        return this.questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with ID " + id,
                        "QuestionService.getQuestionById"));
    }

    /**
     * Retrieves an Answer entity by its ID.
     *
     * @param id The ID of the answer.
     * @return The Answer entity.
     * @throws AnswerNotFoundException if not found.
     */
    private Answer getAnswer(Long id) {
        return this.answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with ID " + id));
    }

}
