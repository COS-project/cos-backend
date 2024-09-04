package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Subject;
import java.util.List;

public class QuestionFixture {

    public static List<NewQuestion> createNewQuestions(Subject subject) {
        return List.of(
                createNewQuestion(1, subject),
                createNewQuestion(2, subject),
                createNewQuestion(3, subject),
                createNewQuestion(4, subject),
                createNewQuestion(5, subject)
        );
    }

    public static NewQuestion createNewQuestion(int seq, Subject subject) {
        return new NewQuestion(
                subject,
                null,
                createOptions(),
                new QuestionContent(seq, "questionTest", 1, 5)
        );
    }

    public static List<QuestionOption> createOptions() {
        return List.of(
                new QuestionOption(1, "option1", "test"),
                new QuestionOption(2, "option2", "test"),
                new QuestionOption(3, "option3", "test"),
                new QuestionOption(4, "option4", "test")
        );
    }

}
