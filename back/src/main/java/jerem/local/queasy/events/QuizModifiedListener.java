package jerem.local.queasy.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import jerem.local.queasy.model.Quiz;
import jerem.local.queasy.service.QuizValidationService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QuizModifiedListener {

    private final QuizValidationService validationService;

    public QuizModifiedListener(QuizValidationService validationService) {
        this.validationService = validationService;
    }

    @EventListener
    public void onQuizModified(QuizModifiedEvent event) {
        log.info("onQuizModified " + event);
        Quiz quiz = event.getQuiz();
        validationService.validate(quiz);
    }
}
