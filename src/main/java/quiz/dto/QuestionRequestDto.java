package quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QuestionRequestDto {

    @NotBlank(message = "Question text cannot be blank")
    private String questionText;

    @NotEmpty(message = "Options list cannot be empty")
    @Size(min = 2, message = "A question must have at least 2 options")
    private List<OptionDto> options;

    public static class OptionDto {

        @NotBlank(message = "Option text cannot be blank")
        private String optionText;

        @com.fasterxml.jackson.annotation.JsonProperty("correct")
        @com.fasterxml.jackson.annotation.JsonAlias({"isCorrect", "correct"})
        private boolean isCorrect;

        public String getOptionText() {
            return optionText;
        }

        public void setOptionText(String optionText) {
            this.optionText = optionText;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            this.isCorrect = correct;
        }
    }
}