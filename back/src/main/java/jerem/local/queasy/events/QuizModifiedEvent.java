package jerem.local.queasy.events;

import org.springframework.context.ApplicationEvent;

import jerem.local.queasy.model.Quiz;
import lombok.Getter;

@Getter
public class QuizModifiedEvent extends ApplicationEvent {
    private final Quiz quiz;

    public QuizModifiedEvent(Object source, Quiz quiz) {
        super(source);
        this.quiz = quiz;
    }

}