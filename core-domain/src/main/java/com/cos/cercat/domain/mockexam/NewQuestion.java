package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.certificate.Subject;
import java.util.List;

public record NewQuestion(
        Subject subject,
        Image image,
        List<QuestionOption> questionOptions,
        QuestionContent questionContent
) {


}
