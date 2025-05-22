package jerem.local.queasy.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception thrown when the username is already taken by another user.
 */
@Slf4j
@Getter
public class UsernameAlreadyExistException extends AbstractAppException {
    private String source;

    public UsernameAlreadyExistException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);

    }

    public UsernameAlreadyExistException(String message) {
        super(message);
        log.error("Message: {}", message);

    }
}