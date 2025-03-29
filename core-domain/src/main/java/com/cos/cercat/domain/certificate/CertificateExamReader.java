package com.cos.cercat.domain.certificate;


import com.cos.cercat.domain.certificate.exception.CertificateException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateExamReader {

    private final CertificateExamRepository certificateExamRepository;

    public CertificateExam readPreviousExam(Certificate certificate) {
        return certificateExamRepository.findPreviousCertificateExam(
                        certificate)
                .orElseThrow(() -> CertificateException.notFoundExam("이전 시험 정보를 찾을 수 없습니다 - " + certificate.id().value()));
    }

    public CertificateExam readNextExam(Certificate certificate) {
        return certificateExamRepository.findNextCertificateExam(certificate)
                .orElseThrow(() -> CertificateException.notFoundExam("다음 시험 정보를 찾을 수 없습니다 - " + certificate.id().value()));
    }

    public List<CertificateExam> read(ExamScheduleType examScheduleType, LocalDateTime dateTime) {
        return switch (examScheduleType) {
            case APPLICATION_START -> certificateExamRepository.findCertificateExamsByApplicationDate(dateTime);
            case DEADLINE -> certificateExamRepository.findCertificateExamsByDeadlineDate(dateTime);
        };
    }
}


