package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.common.File;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
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

    public List<MockExam> find(CertificateId certificateId,
            Integer examYear) {
        Certificate certificate = certificateReader.read(certificateId);
        return mockExamFinder.find(certificate, examYear);
    }

    public List<Integer> findExamYears(CertificateId certificateId) {
        Certificate certificate = certificateReader.read(certificateId);
        return mockExamFinder.findExamYears(certificate);
    }

    public List<Question> findQuestions(MockExamId mockExamId) {
        MockExam mockExam = mockExamReader.read(mockExamId);
        return questionReader.read(mockExam);
    }

    public void createMockExam(
            CertificateId certificateId,
            MockExamInfo mockExamInfo,
            List<QuestionWithSubjectSeq> questions
    ) {
        Certificate certificate = certificateReader.read(certificateId);
        mockExamAppender.append(certificate, mockExamInfo, questions);
    }

    public void insertQuestionImage(Long questionId, File questionImageFile,
            List<UploadingOptionImageFile> optionImageFiles) {
        Question question = questionReader.read(questionId);
        questionUpdater.updateImage(question, questionImageFile, optionImageFiles);
    }
}
