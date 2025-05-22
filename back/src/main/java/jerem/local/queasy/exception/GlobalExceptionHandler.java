package jerem.local.queasy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jerem.local.queasy.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * Global Exception Handler that listen for exceptions thrown from the
 * application It returns
 * ResponseEntity in a standardized response format.
 * 
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractAppException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppException(AbstractAppException ex) {
        HttpStatus status = switch (ex) {
            case QuizNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case QuizAlreadyExistException ignored -> HttpStatus.CONFLICT;
            case BadContextQuizException ignored -> HttpStatus.BAD_REQUEST;
            case QuestionNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case DupplicateQuestionException ignored -> HttpStatus.CONFLICT;
            case InvalidQuestionException ignored -> HttpStatus.BAD_REQUEST;
            case UserNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case UsernameAlreadyExistException ignored -> HttpStatus.CONFLICT;
            case AnswerNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case InvalidQuizException ignored -> HttpStatus.OK;

            default -> HttpStatus.BAD_REQUEST;
        };
        return ResponseEntity.status(status)
                .body(new ErrorResponseDTO(ex.getMessage(), ex.getSource()));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Erreur conversion en nombre: ", ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Erreur de parsing de la requête: ", ex.getMessage()));
    }

}