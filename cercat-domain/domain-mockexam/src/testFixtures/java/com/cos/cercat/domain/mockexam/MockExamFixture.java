package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Certificate;

public class MockExamFixture {

  public static MockExam createMockExam() {
    return new MockExam(
        1,
        MockExamSession.of(2024, 1),
        1000L,
        new Certificate(1L, "testCertificate")
    );
  }

  public static NewMockExam createNewMockExam() {
    return new NewMockExam(
        MockExamSession.of(2024, 1),
        1000L,
        new Certificate(1L, "testCertificate")
    );
  }

}
