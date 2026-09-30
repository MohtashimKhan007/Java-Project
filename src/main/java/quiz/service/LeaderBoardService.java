package quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quiz.model.QuizAttempt;
import quiz.repository.QuizAttemptRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderBoardService {

    private final QuizAttemptRepository quizAttemptRepository;

    public List<QuizAttempt> getTopLeaderboard() {
        Pageable topTen = PageRequest.of(0, 10);
        List<QuizAttempt> topAttempts = quizAttemptRepository.findTopLeaderboardByPercentage(topTen);

        // Strictly rank by percentage descending, then total score descending, then date
        return topAttempts.stream()
                .sorted(Comparator
                        .comparingDouble(QuizAttempt::getPercentage).reversed()
                        .thenComparingInt(QuizAttempt::getScore).reversed()
                        .thenComparing(QuizAttempt::getAttemptedAt, Comparator.nullsLast(Comparator.reverseOrder()))
                )
                .collect(Collectors.toList());
    }
}