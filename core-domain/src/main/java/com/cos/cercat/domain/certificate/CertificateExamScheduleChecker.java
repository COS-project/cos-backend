package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.certificate.event.external.InterestCertificateExamScheduleEvent;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("api")
@RequiredArgsConstructor
public class CertificateExamScheduleChecker {

    private final CertificateExamReader certificateExamReader;
    private final InterestCertificateManager interestCertificateManager;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void check() {
        for (ScheduleCheckType scheduleCheckType : ScheduleCheckType.values()) {
            checkSchedule(scheduleCheckType);
        }
    }

    private void checkSchedule(ScheduleCheckType scheduleCheckType) {
        List<CertificateExam> certificateExams = certificateExamReader.read(
                scheduleCheckType.getExamScheduleType(),
                scheduleCheckType.getDate()
        );

        for (CertificateExam certificateExam : certificateExams) {
            publishEvents(scheduleCheckType, certificateExam);
        }
    }

    private void publishEvents(ScheduleCheckType scheduleCheckType, CertificateExam certificateExam) {
        getTargetUsers(certificateExam).forEach(user -> {
            InterestCertificateExamScheduleEvent event = InterestCertificateExamScheduleEvent.of(
                    certificateExam,
                    user,
                    scheduleCheckType
            );
            eventPublisher.publishEvent(event);
        });
    }

    private List<User> getTargetUsers(CertificateExam certificateExam) {
        return interestCertificateManager.find(certificateExam.certificate()).stream()
                .map(InterestCertificate::user)
                .toList();
    }
}
