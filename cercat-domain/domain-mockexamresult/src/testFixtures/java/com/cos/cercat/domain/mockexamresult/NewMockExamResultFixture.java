package com.cos.cercat.domain.mockexamresult;

public class NewMockExamResultFixture {

  public static NewMockExamResult createNewMockExamResult() {
    return new NewMockExamResult(
        300,
        1,
        NewSubjectResultFixture.createNewSubjectResults()
    );
  }

}
