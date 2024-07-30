package com.cos.cercat.domain.mockexamresult;

import java.util.List;

public class NewUserAnswerFixture {

  public static NewUserAnswer createNewUserAnswer(Long questionId, Integer selectOptionSeq,
      Long takenTime, boolean isCorrect) {
    return new NewUserAnswer(
        questionId,
        selectOptionSeq,
        takenTime,
        isCorrect
    );
  }

  public static List<NewUserAnswer> createNewUserAnswers() {
    return List.of(
        createNewUserAnswer(1L, 1, 1000L, true),
        createNewUserAnswer(2L, 1, 1000L, true),
        createNewUserAnswer(3L, 1, 1000L, true),
        createNewUserAnswer(4L, 1, 1000L, true),
        createNewUserAnswer(5L, 1, 1000L, true));
  }

}
