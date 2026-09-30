package quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import quiz.model.QuizAttempt;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    // all attempt for a specific user
    List<QuizAttempt> findByUserId(Long userId);

    // all attempts for specific quiz
    List<QuizAttempt> findByQuizId(Long quizId);

    // attempts for the combination of user and quiz
    List<QuizAttempt> findByUserIdAndQuizId(Long userId, Long quizId);

    // top 10 scorer person by raw score
    List<QuizAttempt> findAllByOrderByScoreDesc(Pageable pageable);

    // Top leaderboard ranked by percentage descending, then total questions / score descending, then attemptedAt descending
    @Query("SELECT q FROM QuizAttempt q ORDER BY (q.score * 1.0 / CASE WHEN q.totalQuestions = 0 THEN 1 ELSE q.totalQuestions END) DESC, q.score DESC, q.attemptedAt DESC")
    List<QuizAttempt> findTopLeaderboardByPercentage(Pageable pageable);

    // Delete attempts when a quiz is deleted
    @Modifying
    @Transactional
    @Query("DELETE FROM QuizAttempt q WHERE q.quiz.id = :quizId")
    void deleteByQuizId(@Param("quizId") Long quizId);
}