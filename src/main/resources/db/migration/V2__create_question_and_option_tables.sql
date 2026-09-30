CREATE TABLE question (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          question_text TEXT NOT NULL,
                          quiz_id BIGINT,
                          CONSTRAINT fk_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE question_option (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 option_text VARCHAR(255) NOT NULL,
                                 is_correct BOOLEAN NOT NULL,
                                 question_id BIGINT,
                                 CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);