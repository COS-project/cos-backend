package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.Subject;

public record NewQuestion(
        Subject subject,
        QuestionContent questionContent
) {


}
