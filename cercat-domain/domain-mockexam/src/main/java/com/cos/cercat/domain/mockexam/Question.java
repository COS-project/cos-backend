package com.cos.cercat.domain.mockexam;


import com.cos.cercat.domain.certificate.Subject;

public record Question(
        long id,
        MockExam mockExam,
        Subject subject,
        QuestionContent questionContent
) {
}
