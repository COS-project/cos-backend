package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.TargetCertificate;

public interface MockExamRepository {
    Question findQuestion(TargetCertificate targetCertificate,
                          MockExamSession mockExamSession,
                          int questionSequence);
}
