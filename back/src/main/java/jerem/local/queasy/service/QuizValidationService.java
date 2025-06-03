package jerem.local.queasy.service;

import org.springframework.stereotype.Service;

import jerem.local.queasy.configuration.properties.QuizConfigProperties;
import jerem.local.queasy.model.Question;
import jerem.local.queasy.model.Quiz;
import jerem.local.queasy.repository.QuizRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for validating a quiz against configurable business
 * rules,
 * such as number of questions, answers per question, and correctness
 * constraints.
 */
@Slf4j
@Service
@Data
public class QuizValidationService {

    private final QuizConfigProperties quizConfigProperties;
    private final QuizRepository quizRepository;

    /**
     * Constructor for dependency injection.
     *
     * @param quizConfigProperties Configuration rules for quizzes (min/max
     *                             questions, answers...).
     * @param quizRepository       Repository used to persist quiz validation
     *                             status.
     */
    public QuizValidationService(QuizConfigProperties quizConfigProperties, QuizRepository quizRepository) {
        this.quizConfigProperties = quizConfigProperties;
        this.quizRepository = quizRepository;
    }

    /**
     * Validates the provided quiz according to rules from
     * {@link QuizConfigProperties}.
     * <ul>
     * <li>Number of questions must be between defined min and max.</li>
     * <li>Each question must have a number of answers within limits.</li>
     * <li>Each question must have at least one correct answer.</li>
     * </ul>
     * The result is stored in the {@code valid} and {@code validationMessage}
     * fields of the quiz,
     * and the updated quiz is saved to the database.
     *
     * @param quiz The quiz to validate.
     */
    public void validate(Quiz quiz) {
        log.info("QuizValidationService.validate");

        StringBuilder message = new StringBuilder();
        boolean valid = true;

        int questionCount = quiz.getQuestions().size();

        if (questionCount < quizConfigProperties.getMinnumberofquestions()) {
            valid = false;
            message.append("Quiz contains ").append(questionCount)
                    .append(" questions, which is less than the minimum required (")
                    .append(quizConfigProperties.getMinnumberofquestions()).append("). ");
        }

        if (questionCount > quizConfigProperties.getMaxnumberofquestions()) {
            valid = false;
            message.append("Quiz contains ").append(questionCount)
                    .append(" questions, which exceeds the maximum allowed (")
                    .append(quizConfigProperties.getMaxnumberofquestions()).append("). ");
        }

        for (Question question : quiz.getQuestions()) {
            int answerCount = question.getAnswers().size();

            if (question.getAnswers().stream().noneMatch(answer -> Boolean.TRUE.equals(answer.getCorrect()))) {
                valid = false;
                message.append("Question ").append(question.getId())
                        .append(" must have at least one correct answer. ");
            }

            if (answerCount < quizConfigProperties.getMinanswersperquestion()) {
                valid = false;
                message.append("Question ").append(question.getId())
                        .append(" has ").append(answerCount)
                        .append(" answers, which is below the minimum required (")
                        .append(quizConfigProperties.getMinanswersperquestion()).append("). ");
            }

            if (answerCount > quizConfigProperties.getMaxanswersperquestion()) {
                valid = false;
                message.append("Question ").append(question.getId())
                        .append(" has ").append(answerCount)
                        .append(" answers, which exceeds the maximum allowed (")
                        .append(quizConfigProperties.getMaxanswersperquestion()).append("). ");
            }
        }

        quiz.setValid(valid);
        quiz.setValidationMessage(valid ? null : message.toString().trim());
        quizRepository.save(quiz);
    }
}
