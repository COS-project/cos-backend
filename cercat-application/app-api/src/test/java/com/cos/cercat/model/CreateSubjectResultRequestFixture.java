package com.cos.cercat.model;

import com.cos.cercat.apis.mockExamResult.request.CreateSubjectResultRequest;
import java.util.List;

public class CreateSubjectResultRequestFixture {

  public static CreateSubjectResultRequest createSubjectResultRequest(int score) {
    return new CreateSubjectResultRequest(
        1L,
        score,
        CreateUserAnswerRequestFixture.createUserAnswerRequests()
    );
  }

}
