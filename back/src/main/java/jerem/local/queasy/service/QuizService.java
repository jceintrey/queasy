package jerem.local.queasy.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jerem.local.queasy.dto.QuestionDetailedDTO;
import jerem.local.queasy.dto.QuestionRequestDTO;
import jerem.local.queasy.dto.QuizCreationRequestDTO;
import jerem.local.queasy.dto.QuizSummaryDTO;
import jerem.local.queasy.events.QuizModifiedEvent;
import jerem.local.queasy.exception.BadContextQuizException;
import jerem.local.queasy.exception.DupplicateQuestionException;
import jerem.local.queasy.exception.QuestionNotFoundException;
import jerem.local.queasy.exception.QuizAlreadyExistException;
import jerem.local.queasy.exception.QuizNotFoundException;
import jerem.local.queasy.mapper.QuizMapper;
import jerem.local.queasy.model.Question;
import jerem.local.queasy.model.Quiz;
import jerem.local.queasy.repository.QuestionRepository;
import jerem.local.queasy.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for managing quizzes and their associated questions.
 */
@Service
@Slf4j
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final QuestionService questionService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor with dependency injection.
     */
    public QuizService(QuestionRepository questionRepository, QuizRepository quizRepository, QuizMapper quizMapper,
            QuestionService questionService, ApplicationEventPublisher eventPublisher) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.questionService = questionService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Retrieves all quizzes as summary DTOs.
     *
     * @return List of all quizzes in summary form.
     */
    public List<QuizSummaryDTO> getAll() {
        return this.quizMapper.toDto(this.quizRepository.findAll());
    }

    /**
     * Creates a new quiz.
     *
     * @param quizRequest The request DTO containing title and level.
     * @return The created quiz as a summary DTO.
     * @throws QuizAlreadyExistException if a quiz with the same title already
     *                                   exists.
     */
    public QuizSummaryDTO create(QuizCreationRequestDTO quizRequest) {
        if (this.quizRepository.existsByTitle(quizRequest.getTitle())) {
            throw new QuizAlreadyExistException("Quiz title already used", "QuizService.create");
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setLevel(quizRequest.getLevel());
        quiz.setValid(false);
        quizRepository.save(quiz);

        eventPublisher.publishEvent(new QuizModifiedEvent(this, quiz));

        return quizMapper.toDto(quiz);
    }

    /**
     * Retrieves a specific quiz by its ID.
     *
     * @param id The ID of the quiz.
     * @return The quiz as a summary DTO.
     * @throws QuizNotFoundException if the quiz does not exist.
     */
    public QuizSummaryDTO getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new QuizNotFoundException("Quiz not found with id " + id, "QuizService.getQuizById"));
        return quizMapper.toDto(quiz);
    }

    /**
     * Deletes a quiz by its ID.
     *
     * @param quizId The ID of the quiz to delete.
     * @throws QuizNotFoundException if the quiz does not exist.
     */
    public void deleteById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new QuizNotFoundException("Quiz not found with id " + quizId, "QuizService.getQuizById"));
        this.quizRepository.delete(quiz);
    }

    /**
     * Removes a question from a quiz.
     *
     * @param quizId     The ID of the quiz.
     * @param questionId The ID of the question to remove.
     * @throws QuestionNotFoundException if the question does not exist.
     * @throws QuizNotFoundException     if the quiz does not exist.
     * @throws BadContextQuizException   if the question does not belong to the
     *                                   quiz.
     */
    public void removeQuestionFromQuiz(Long quizId, Long questionId) {
        log.info("Remove Question " + questionId + " from quiz " + quizId);

        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new QuestionNotFoundException("Question not found with id " + questionId,
                        "QuestionService.deleteById"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id " + quizId,
                        "QuizService.deleteQuestionFromQuiz"));

        if (!quiz.getQuestions().contains(question)) {
            throw new BadContextQuizException(
                    "Question with id " + questionId + " does not belong to quiz with id " + quizId,
                    "QuizService.deleteQuestionFromQuiz");
        }

        quiz.getQuestions().remove(question);
        quizRepository.save(quiz);

        eventPublisher.publishEvent(new QuizModifiedEvent(this, quiz));
    }

    /**
     * Adds a new question to a given quiz.
     *
     * @param questionRequest The DTO representing the new question.
     * @param quizId          The ID of the quiz.
     * @return The created question as a detailed DTO.
     * @throws QuizNotFoundException       if the quiz does not exist.
     * @throws DupplicateQuestionException if a question with the same text already
     *                                     exists in the quiz.
     */
    public QuestionDetailedDTO addNewQuestion(QuestionRequestDTO questionRequest, Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id " + quizId,
                        "QuizService.deleteQuestionFromQuiz"));

        if (quiz.getQuestions().stream().anyMatch(q -> q.getText().equalsIgnoreCase(questionRequest.getText()))) {
            throw new DupplicateQuestionException("Question already exist in quiz with the given title");
        }

        QuestionDetailedDTO created = this.questionService.addNewQuestion(questionRequest, quiz);
        eventPublisher.publishEvent(new QuizModifiedEvent(this, quiz));

        return created;
    }

    /**
     * Marks a quiz as validated (functionally placeholder for now).
     *
     * @param quizId The ID of the quiz to validate.
     * @return The validated quiz as a summary DTO.
     * @throws QuizNotFoundException if the quiz does not exist.
     */
    public QuizSummaryDTO validate(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id " + quizId,
                        "QuizService.validate"));

        eventPublisher.publishEvent(new QuizModifiedEvent(this, quiz));
        return quizMapper.toDto(quiz);
    }

}
