package jerem.local.queasy.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception thrown when the quiz title is already used.
 */
@Slf4j
@Getter
public class QuizAlreadyExistException extends AbstractAppException {

    public QuizAlreadyExistException(String message, String source) {
        super(message);
    }

    public QuizAlreadyExistException(String message) {
        super(message);
    }
}