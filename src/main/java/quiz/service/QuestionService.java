package quiz.service;

import org.springframework.stereotype.Service;
import quiz.dto.QuestionRequestDto;
import quiz.model.Option;
import quiz.model.Question;
import quiz.model.Quiz;
import quiz.repository.QuestionRepository;
import quiz.repository.QuizRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    // Constructor-based dependency injection
    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    // Add Question to Quiz
    public Question addQuestionToQuiz(Long quizId, QuestionRequestDto request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));

        Question question = new Question();
        question.setQuestionText(request.getQuestionText());
        question.setQuiz(quiz);

        if (request.getOptions() != null) {
            List<Option> options = request.getOptions().stream().map(dto -> {
                Option option = new Option();
                option.setOptionText(dto.getOptionText());
                option.setCorrect(dto.isCorrect());
                option.setQuestion(question); // Bidirectional mapping
                return option;
            }).collect(Collectors.toList());

            question.setOptions(options);
        }

        return questionRepository.save(question);
    }

    // Get all questions for a specific quiz
    public List<Question> getQuestionsByQuizId(Long quizId) {

        if (!quizRepository.existsById(quizId)) {
            throw new RuntimeException("Quiz not found with id: " + quizId);
        }
        return questionRepository.findByQuizId(quizId);
    }

    // Delete a question (ADMIN only)
    public void deleteQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new RuntimeException("Question not found with id: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }
}