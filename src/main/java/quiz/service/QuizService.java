package quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quiz.dto.QuizRequestDto;
import quiz.dto.QuizResultResponseDto;
import quiz.dto.QuizSubmissionRequestDto;
import quiz.model.Category;
import quiz.model.Option;
import quiz.model.Question;
import quiz.model.Quiz;
import quiz.model.QuizAttempt;
import quiz.model.User;
import quiz.repository.CategoryRepository;
import quiz.repository.QuestionRepository;
import quiz.repository.QuizAttemptRepository;
import quiz.repository.QuizRepository;
import quiz.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, CategoryRepository categoryRepository, QuestionRepository questionRepository, QuizAttemptRepository quizAttemptRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.userRepository = userRepository;
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Create quiz using QuizRequest DTO with relational Category
    public Quiz createQuiz(QuizRequestDto request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setCategory(category);

        return quizRepository.save(quiz);
    }

    // Delete quiz and all its related attempts and cascade questions
    @Transactional
    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new RuntimeException("Quiz not found with id: " + id);
        }
        quizAttemptRepository.deleteByQuizId(id);
        quizRepository.deleteById(id);
    }

    // Evaluate quiz submission and calculate score based on actual total questions in the quiz
    public QuizResultResponseDto evaluateQuiz(QuizSubmissionRequestDto request) {
        List<QuizSubmissionRequestDto.QuestionAnswerDto> userAnswers = request.getAnswers();

        // Fetch the quiz from database to get the actual total questions count
        Quiz quiz = quizRepository.findById(request.getQuizId()).orElse(null);
        int totalQuestionsInQuiz = (quiz != null && quiz.getQuestions() != null) ? quiz.getQuestions().size() : 0;

        // Fallback to answers size if quiz questions list is empty or null
        if (totalQuestionsInQuiz == 0) {
            totalQuestionsInQuiz = userAnswers != null ? userAnswers.size() : 0;
        }

        int correctCount = 0;

        if (userAnswers != null) {
            for (QuizSubmissionRequestDto.QuestionAnswerDto answer : userAnswers) {
                // Fetch question from database
                Question question = questionRepository.findById(answer.getQuestionId()).orElse(null);

                if (question != null && question.getOptions() != null) {
                    // Match user's selected option with the correct option in database
                    for (Option option : question.getOptions()) {
                        if (option.getId().equals(answer.getSelectedOptionId()) && option.isCorrect()) {
                            correctCount++;
                            break;
                        }
                    }
                }
            }
        }

        // Unanswered or wrong questions count calculation
        int wrongCount = totalQuestionsInQuiz - correctCount;

        // Calculate percentage based on the actual total questions of the quiz
        double percentage = totalQuestionsInQuiz > 0 ? ((double) correctCount / totalQuestionsInQuiz) * 100 : 0.0;

        // Persist or update quiz attempt for leaderboard
        if (quiz != null) {
            User user = null;
            if (request.getUsername() != null && !request.getUsername().isBlank()) {
                user = userRepository.findByUsername(request.getUsername()).orElse(null);
            } else if (request.getUserId() != null) {
                user = userRepository.findById(request.getUserId()).orElse(null);
            }

            if (user != null) {
                List<QuizAttempt> existingAttempts = quizAttemptRepository.findByUserIdAndQuizId(user.getId(), quiz.getId());
                if (!existingAttempts.isEmpty()) {
                    QuizAttempt bestAttempt = existingAttempts.get(0);
                    for (QuizAttempt a : existingAttempts) {
                        if (a.getScore() > bestAttempt.getScore()) {
                            bestAttempt = a;
                        }
                    }
                    if (correctCount >= bestAttempt.getScore()) {
                        bestAttempt.setScore(correctCount);
                        bestAttempt.setTotalQuestions(totalQuestionsInQuiz);
                        bestAttempt.setAttemptedAt(LocalDateTime.now());
                        quizAttemptRepository.save(bestAttempt);
                    }
                } else {
                    QuizAttempt attempt = new QuizAttempt();
                    attempt.setQuiz(quiz);
                    attempt.setUser(user);
                    attempt.setScore(correctCount);
                    attempt.setTotalQuestions(totalQuestionsInQuiz);
                    attempt.setAttemptedAt(LocalDateTime.now());
                    quizAttemptRepository.save(attempt);
                }
            }
        }

        return new QuizResultResponseDto(totalQuestionsInQuiz, correctCount, wrongCount, percentage);
    }
}