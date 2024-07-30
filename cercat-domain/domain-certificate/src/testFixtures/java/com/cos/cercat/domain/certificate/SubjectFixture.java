package com.cos.cercat.domain.certificate;

import java.util.List;

public class SubjectFixture {

  public static List<SubjectInfo> createInfos() {
    return List.of(
        new SubjectInfo(1, "subject1", 30, 30),
        new SubjectInfo(2, "subject2", 30, 30),
        new SubjectInfo(3, "subject3", 30, 30),
        new SubjectInfo(4, "subject4", 30, 30),
        new SubjectInfo(5, "subject5", 30, 30)
    );
  }

  public static Subject createSubject() {
    return new Subject(
        1L,
        new Certificate(1L, "testCertificate"),
        new SubjectInfo(1, "subject1", 30, 30)
    );
  }
}
