package quiz.entity;

import jakarta.persistence.*;
import lombok.Data;
import quiz.model.Question;

@Entity
@Table(name = "question_option")
@Data
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String optionText;

    @Column(nullable = false)
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}