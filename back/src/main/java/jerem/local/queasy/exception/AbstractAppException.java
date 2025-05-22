
package jerem.local.queasy.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public abstract class AbstractAppException extends RuntimeException {
    private final String source;

    protected AbstractAppException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);
    }

    protected AbstractAppException(String message) {
        super(message);
        this.source = null;
        log.error("Message: {}", message);
    }
}
