package quiz.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quiz.dto.QuizSubmissionRequestDto;
import quiz.model.QuizAttempt;
import quiz.service.QuizAttemptService;

@RestController
@RequestMapping("/api/attempts")
public class QuizAttemptController {
    private final QuizAttemptService quizAttemptService;
    public QuizAttemptController(QuizAttemptService quizAttemptService){
        this.quizAttemptService = quizAttemptService;
    }

    @PostMapping("/quiz/{quizId}/user/{userId}")
    public ResponseEntity<QuizAttempt> submitQuiz(@PathVariable Long quizId,
                                                  @PathVariable Long userId,
                                                  @RequestBody QuizSubmissionRequestDto submissionRequest){

        QuizAttempt attempt = quizAttemptService.submitQuiz(quizId,userId,submissionRequest);
        return ResponseEntity.ok(attempt);
    }

}
