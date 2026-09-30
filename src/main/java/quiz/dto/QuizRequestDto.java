package quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizRequestDto {
    private String title;
    private String description;
    private Long categoryId;
}