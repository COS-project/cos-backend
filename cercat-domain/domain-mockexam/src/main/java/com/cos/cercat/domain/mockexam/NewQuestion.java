package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Subject;

public record NewQuestion(
        Subject subject,
        QuestionContent questionContent
) {


}
