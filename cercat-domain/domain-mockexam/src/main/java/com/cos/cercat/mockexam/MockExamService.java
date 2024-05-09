package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MockExamService {

    private final MockExamFinder mockExamFinder;
    private final QuestionFinder questionFinder;
    private final CertificateReader certificateReader;
    private final MockExamReader mockExamReader;

    public List<MockExam> find(TargetCertificate targetCertificate,
                               Integer examYear) {
        Certificate certificate = certificateReader.read(targetCertificate);
        return mockExamFinder.find(certificate, examYear);
    }

    public List<Integer> findExamYears(TargetCertificate targetCertificate) {
        Certificate certificate = certificateReader.read(targetCertificate);
        return mockExamFinder.findExamYears(certificate);
    }

    public List<Question> findQuestions(TargetMockExam targetMockExam) {
        MockExam mockExam = mockExamReader.read(targetMockExam);
        return questionFinder.find(mockExam);
    }
}
