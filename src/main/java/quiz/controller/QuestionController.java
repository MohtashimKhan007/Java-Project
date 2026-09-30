package quiz.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import quiz.dto.QuestionRequestDto;
import quiz.model.Question;
import quiz.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Add a question to a specific quiz using QuestionRequest DTO (ADMIN only)
    @PostMapping("/{quizId}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Question> addQuestionToQuiz(
            @PathVariable Long quizId,
           @Valid @RequestBody QuestionRequestDto request) {
        Question savedQuestion = questionService.addQuestionToQuiz(quizId, request);
        return ResponseEntity.ok(savedQuestion);
    }

    // Get all questions for a specific quiz
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByQuiz(@PathVariable Long quizId) {
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    // Delete a question by ID (ADMIN only)
    @DeleteMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Question deleted successfully!");
    }
}