package jerem.local.queasy.service;

import org.springframework.stereotype.Service;

import jerem.local.queasy.configuration.properties.QuizConfigProperties;
import jerem.local.queasy.model.Question;
import jerem.local.queasy.model.Quiz;
import jerem.local.queasy.repository.QuizRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Data
public class QuizValidationService {

    private final QuizConfigProperties quizConfigProperties;
    private final QuizRepository quizRepository;

    public QuizValidationService(QuizConfigProperties quizConfigProperties, QuizRepository quizRepository) {

        this.quizConfigProperties = quizConfigProperties;
        this.quizRepository = quizRepository;
    }

    public void validate(Quiz quiz) {
        log.info("QuizValidationService.validate");
        StringBuilder message = new StringBuilder();
        boolean valid = true;

        if (quiz.getQuestions().size() < quizConfigProperties.getMinnumberofquestions()) {
            valid = false;

            message.append(
                    "Total number of questions is " + quiz.getQuestions().size() + " and must be greater or equal than "
                            + quizConfigProperties.getMinnumberofquestions() + '.');
        }

        if (quiz.getQuestions().size() > quizConfigProperties.getMaxnumberofquestions()) {
            valid = false;
            message.append("Total number of questions is " + quiz.getQuestions().size() + " and must be less than "
                    + quizConfigProperties.getMaxnumberofquestions() + ".");
        }

        for (Question question : quiz.getQuestions()) {
            int size = question.getAnswers().size();

            if (question.getAnswers().stream().noneMatch((answer) -> answer.getCorrect())) {
                valid = false;
                message.append("Question ").append(question.getId())
                        .append(" should contains at least one correct answer");
            }

            if (size < quizConfigProperties.getMinanswersperquestion()) {
                valid = false;
                message.append("Question ").append(question.getId())
                        .append(" contains ")
                        .append(size)
                        .append(" answers and must contains at least ")
                        .append(quizConfigProperties.getMinanswersperquestion())
                        .append(" answers.");
            }

            if (size > quizConfigProperties.getMaxanswersperquestion()) {
                valid = false;
                message.append("Question ").append(question.getId())
                        .append(" contains ")
                        .append(size)
                        .append(" answers and must contains less than ")
                        .append(quizConfigProperties.getMinanswersperquestion())
                        .append(" answers.");
            }
        }

        quiz.setValid(valid);
        quiz.setValidationMessage(valid ? null : message.toString().trim());
        quizRepository.save(quiz);

    }
}
