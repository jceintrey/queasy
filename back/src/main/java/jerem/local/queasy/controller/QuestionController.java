package jerem.local.queasy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jerem.local.queasy.dto.AnswerDetailedDTO;
import jerem.local.queasy.dto.AnswerRequestDTO;
import jerem.local.queasy.dto.QuestionDetailedDTO;
import jerem.local.queasy.service.QuestionService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller class used for question purpose.
 * <p>
 * This class implements the quiz related question endpoints like getting a
 * question, create etc..
 * </p>
 * <p>
 * -
 * 
 * </p>
 * 
 */
@RestController
@RequestMapping("/api/questions")
@Slf4j
@Tag(name = "QuestionController", description = "Process question related CRUD operations")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Operation(summary = "get question by its Id", description = "This endpoint allows a user to retrieve a question given its Id.")
    @ApiResponse(responseCode = "200", description = "Successful, return question ")
    @ApiResponse(responseCode = "404", description = "Error, Question not found ")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDetailedDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @Operation(summary = "Update an answer of a question", description = "This endpoint allows a user to update an question answer given its id and question id.")
    @ApiResponse(responseCode = "200", description = "Successful, return answer ")
    @ApiResponse(responseCode = "404", description = "Error, Question not found ")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PatchMapping("/{questionId}/answers/{answerId}")
    public ResponseEntity<AnswerDetailedDTO> updateQuestionAnswer(@PathVariable Long questionId,
            @PathVariable Long answerId, @RequestBody AnswerRequestDTO answerRequestDTO) {
        log.info("Update an answer with dto " + answerRequestDTO.toString());
        log.info("questionId " + questionId + " answerId " + answerId);
        return ResponseEntity.ok(questionService.updateQuestionAnswer(questionId, answerId, answerRequestDTO));
    }

    @Operation(summary = "Add an answer to a question", description = "This endpoint allows a user to add an answer to a question given question id.")
    @ApiResponse(responseCode = "200", description = "Successful, return answer ")
    @ApiResponse(responseCode = "404", description = "Error, Question not found ")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("/{questionId}/answers/")
    public ResponseEntity<AnswerDetailedDTO> addQuestionAnswer(@PathVariable Long questionId,
            @RequestBody AnswerRequestDTO answerRequestDTO) {
        return ResponseEntity.ok(questionService.addAnswerToQuestion(questionId, answerRequestDTO));
    }

    @Operation(summary = "Remove an answer from a question", description = "This endpoint allows a user to remove an answer from a question given question id.")
    @ApiResponse(responseCode = "204", description = "Answer successfully deleted ")
    @ApiResponse(responseCode = "404", description = "Error, Question not found ")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @DeleteMapping("/{questionId}/answers/{answerId}")
    public ResponseEntity<QuestionDetailedDTO> removeAnswerFromQuestion(@PathVariable Long questionId,
            @PathVariable Long answerId) {

        return ResponseEntity.ok(questionService.removeAnswerFromQuestion(questionId, answerId));
    }

}
