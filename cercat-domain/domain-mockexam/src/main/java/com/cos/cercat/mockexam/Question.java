package com.cos.cercat.mockexam;


import com.cos.cercat.certificate.Subject;

public record Question(
        long id,
        MockExam mockExam,
        Subject subject,
        QuestionContent questionContent
) {
}
