package quiz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz.model.QuizAttempt;
import quiz.service.LeaderBoardService;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderBoardService leaderBoardService;

    @GetMapping
    public ResponseEntity<List<QuizAttempt>> getLeaderboard() {
        List<QuizAttempt> topScores = leaderBoardService.getTopLeaderboard();
        return ResponseEntity.ok(topScores);
    }
}