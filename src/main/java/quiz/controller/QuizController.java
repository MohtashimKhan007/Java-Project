package quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import quiz.dto.QuizRequestDto;
import quiz.dto.QuizResultResponseDto;
import quiz.dto.QuizSubmissionRequestDto;
import quiz.model.Quiz;
import quiz.service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    //getting all quizzes
    @GetMapping
    public List<Quiz> getAllQuizes(){
        return quizService.getAllQuizzes();
    }

    //creating new quiz
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequestDto request){
        Quiz savedQuiz = quizService.createQuiz(request);
        return ResponseEntity.ok(savedQuiz);
    }

    //submitting the quiz
    @PostMapping("/submit")
    public ResponseEntity<QuizResultResponseDto> submitQuiz(@RequestBody QuizSubmissionRequestDto request) {
        QuizResultResponseDto result = quizService.evaluateQuiz(request);
        return ResponseEntity.ok(result);
    }

    //deleting a quiz (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz deleted successfully!");
    }
}