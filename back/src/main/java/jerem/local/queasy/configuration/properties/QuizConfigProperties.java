package jerem.local.queasy.configuration.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(prefix = "queasy.quiz")
public class QuizConfigProperties {
    private int minnumberofquestions;
    private int maxnumberofquestions;
    private int minanswersperquestion;
    private int maxanswersperquestion;
}
