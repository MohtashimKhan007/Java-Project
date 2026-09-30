package quiz.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QuizSubmissionRequestDto {
    private Long userId;
    private String username;
    private Long quizId;
    private List<QuestionAnswerDto> answers;

    @Getter
    @Setter
    public static class QuestionAnswerDto {
        private Long questionId;
        private Long selectedOptionId;
    }
}