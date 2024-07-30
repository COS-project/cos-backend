package com.cos.cercat.model;

import com.cos.cercat.apis.mockExamResult.request.CreateUserAnswerRequest;
import java.util.List;

public class CreateUserAnswerRequestFixture {

  public static CreateUserAnswerRequest createUserAnswerRequest(Long questionId, Integer selectOptionSeq, Long takenTime, boolean isCorrect) {
    return new CreateUserAnswerRequest(
        questionId,
        selectOptionSeq,
        takenTime,
        isCorrect
    );
  }

  public static List<CreateUserAnswerRequest> createUserAnswerRequests() {
    return List.of(
        createUserAnswerRequest(1L, 1, 100L, true),
        createUserAnswerRequest(2L, 2, 200L, false),
        createUserAnswerRequest(3L, 3, 300L, true)
    );
  }
}
