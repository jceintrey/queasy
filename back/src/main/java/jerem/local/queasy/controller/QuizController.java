package jerem.local.queasy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jerem.local.queasy.dto.QuestionDetailedDTO;
import jerem.local.queasy.dto.QuestionRequestDTO;
import jerem.local.queasy.dto.QuizCreationRequestDTO;
import jerem.local.queasy.dto.QuizResultDTO;
import jerem.local.queasy.dto.QuizSummaryDTO;
import jerem.local.queasy.dto.UserAnswersDTO;
import jerem.local.queasy.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller class used for quizz purpose.
 * <p>
 * This class implements the quiz endpoints like getting a new quiz
 * </p>
 * <p>
 * - {@link QuizService} service for Quiz operation
 * -
 * </p>
 * 
 */
@RestController
@RequestMapping("/api/quizzes")
@Slf4j
@Tag(name = "QuizController", description = "Process quiz related operations")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;

    }

    @Operation(summary = "get quizzes list", description = "This endpoint allows a user to retrieve the whole list of available Quizzes.")
    @ApiResponse(responseCode = "200", description = "Successful, return quizz list")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping("")
    public ResponseEntity<List<QuizSummaryDTO>> getAll() {
        return ResponseEntity.ok(quizService.getAll());
    }

    @Operation(summary = "create a new Empty quiz", description = "This endpoint allows a user to create a new Empty quizz.")
    @ApiResponse(responseCode = "200", description = "Successful, return quizz list")
    @ApiResponse(responseCode = "409", description = "Conflict, already exist")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("")
    public ResponseEntity<QuizSummaryDTO> createQuiz(@RequestBody QuizCreationRequestDTO quizRequest) {
        return ResponseEntity.ok(quizService.create(quizRequest));
    }

    @Operation(summary = "get quiz by its Id", description = "This endpoint allows a user to retrieve a quiz given its Id.")
    @ApiResponse(responseCode = "200", description = "Successful, return quiz ")
    @ApiResponse(responseCode = "404", description = "Error, Quiz not found ")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizSummaryDTO> getById(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    @Operation(summary = "delete quiz by its Id", description = "This endpoint allows a user to delete a quiz given its Id.")
    @ApiResponse(responseCode = "204", description = "Quiz successfully deleted ")
    @ApiResponse(responseCode = "404", description = "Error, Quiz not found ")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long quizId) {
        quizService.deleteById(quizId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "delete question by its Id", description = "This endpoint allows a user to delete a question given its Id.")
    @ApiResponse(responseCode = "204", description = "question successfully deleted ")
    @ApiResponse(responseCode = "404", description = "Error, question not found ")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @DeleteMapping("/{quizId}/question/{questionId}")
    public ResponseEntity<Void> removeQuestionFromQuiz(@PathVariable Long quizId, @PathVariable Long questionId) {
        quizService.removeQuestionFromQuiz(quizId, questionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "create and add and question to quiz", description = "This endpoint allows a user to add a new question to the quizz.")
    @ApiResponse(responseCode = "200", description = "Successful, return question")
    @ApiResponse(responseCode = "409", description = "Conflict, question with same title already exist")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("{quizId}/questions/")
    public ResponseEntity<QuestionDetailedDTO> addNewQuestion(@PathVariable Long quizId,
            @RequestBody QuestionRequestDTO questionRequest) {
        log.info(questionRequest.getText());
        log.info(questionRequest.getAnswers().toString());
        return ResponseEntity.ok(quizService.addNewQuestion(questionRequest, quizId));
    }

    @Operation(summary = "validate the quiz conformity", description = "This endpoint allows a user to validate their quiz against quiz rules.")
    @ApiResponse(responseCode = "200", description = "Successful, return question")
    @ApiResponse(responseCode = "409", description = "Conflict, question with same title already exist")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("{quizId}/validate")
    public ResponseEntity<QuizSummaryDTO> validateQuiz(@PathVariable Long quizId) {

        return ResponseEntity.ok(quizService.validate(quizId));
    }

    @Operation(summary = "submit quiz answers", description = "This endpoint allows a user to submit their quiz answers.")
    @ApiResponse(responseCode = "200", description = "Successful, return score")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("{quizId}/answers")
    public ResponseEntity<QuizResultDTO> submitQuizAnswers(@PathVariable Long quizId,
            @RequestBody UserAnswersDTO userAnswersDTO) {

        return ResponseEntity.ok(quizService.submitQuizAnswers(quizId, userAnswersDTO));
    }

}
