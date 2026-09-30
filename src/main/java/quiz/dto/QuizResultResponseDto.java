package quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizResultResponseDto {
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private double scorePercentage;
}