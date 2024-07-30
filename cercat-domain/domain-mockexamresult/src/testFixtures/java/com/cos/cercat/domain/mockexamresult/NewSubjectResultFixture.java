package com.cos.cercat.domain.mockexamresult;

import java.util.List;

public class NewSubjectResultFixture {

  public static NewSubjectResult createNewSubjectResult(Long subjectId, int score, int numOfCorrect,
      long takenTime) {
    return new NewSubjectResult(
        subjectId,
        score,
        numOfCorrect,
        takenTime,
        NewUserAnswerFixture.createNewUserAnswers()
    );
  }

  public static List<NewSubjectResult> createNewSubjectResults() {
    return List.of(
        createNewSubjectResult(1L, 100, 10, 1000),
        createNewSubjectResult(2L, 100, 10, 1000),
        createNewSubjectResult(3L, 100, 10, 1000),
        createNewSubjectResult(4L, 100, 10, 1000),
        createNewSubjectResult(5L, 100, 10, 1000));
  }
}
