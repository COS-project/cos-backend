package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.common.File;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MockExamService {

    private final MockExamFinder mockExamFinder;
    private final MockExamAppender mockExamAppender;
    private final QuestionReader questionReader;
    private final CertificateReader certificateReader;
    private final MockExamReader mockExamReader;
    private final QuestionUpdater questionUpdater;

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
        return questionReader.read(mockExam);
    }

    public void createMockExam(TargetCertificate targetCertificate,
            MockExamSession session,
            Long timeLimit,
            List<QuestionWithSubjectSeq> contents) {
        Certificate certificate = certificateReader.read(targetCertificate);
        mockExamAppender.append(certificate, session, timeLimit, contents);
    }

    public void insertQuestionImage(Long questionId, File questionImageFile,
            List<UploadingOptionImageFile> optionImageFiles) {
        Question question = questionReader.read(questionId);
        questionUpdater.updateImage(question, questionImageFile, optionImageFiles);
    }
}
