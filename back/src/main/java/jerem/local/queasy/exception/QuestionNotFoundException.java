package jerem.local.queasy.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class QuestionNotFoundException extends AbstractAppException {
    private String source;

    public QuestionNotFoundException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);

    }

    public QuestionNotFoundException(String message) {
        super(message);
        log.error("Message: {}", message);

    }
}
