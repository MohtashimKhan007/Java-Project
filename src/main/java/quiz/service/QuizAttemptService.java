package quiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quiz.dto.QuizSubmissionRequestDto;
import quiz.model.Option;
import quiz.model.Question;
import quiz.model.Quiz;
import quiz.model.QuizAttempt;
import quiz.model.User;
import quiz.repository.QuizAttemptRepository;
import quiz.repository.QuizRepository;
import quiz.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizAttemptService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    @Transactional
    public QuizAttempt submitQuiz(Long quizId, Long userId, QuizSubmissionRequestDto submissionRequest) {
        log.info("Starting quiz evaluation for Quiz ID: {} by User ID: {}", quizId, userId);

        //  Fetch Quiz with validation
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> {
                    log.error("Quiz not found with id: {}", quizId);
                    return new RuntimeException("Quiz not found with id: " + quizId);
                });

        //  Fetch User with validation
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new RuntimeException("User not found with id: " + userId);
                });

        //  Map questions and their correct options safely
        Map<Long, Long> questionToCorrectOptionMap = quiz.getQuestions().stream()
                .collect(Collectors.toMap(
                        Question::getId,
                        question -> question.getOptions().stream()
                                .filter(Option::isCorrect)
                                .map(Option::getId)
                                .findFirst()
                                .orElse(-1L),
                        (existing, replacement) -> existing
                ));

        int totalQuestions = quiz.getQuestions().size();
        int score = 0;

        // Safely extract and evaluate user answers from List<QuestionAnswerDto>
        List<QuizSubmissionRequestDto.QuestionAnswerDto> userAnswers =
                (submissionRequest != null && submissionRequest.getAnswers() != null)
                        ? submissionRequest.getAnswers()
                        : Collections.emptyList();

        for (QuizSubmissionRequestDto.QuestionAnswerDto answer : userAnswers) {
            Long questionId = answer.getQuestionId();
            Long selectedOptionId = answer.getSelectedOptionId();

            Long correctOptionId = questionToCorrectOptionMap.get(questionId);
            if (correctOptionId != null && correctOptionId.equals(selectedOptionId)) {
                score++;
            }
        }

        //  Build and persist QuizAttempt record
        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setUser(user);
        attempt.setScore(score);
        attempt.setTotalQuestions(totalQuestions);
        attempt.setAttemptedAt(LocalDateTime.now());

        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);
        log.info("Successfully evaluated and saved quiz attempt ID: {} with score: {}/{}",
                savedAttempt.getId(), score, totalQuestions);

        return savedAttempt;
    }
}