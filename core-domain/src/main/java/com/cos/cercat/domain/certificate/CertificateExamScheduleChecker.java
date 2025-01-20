package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.common.EventPublisher;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("api")
@RequiredArgsConstructor
public class CertificateExamScheduleChecker {

    private final CertificateExamReader certificateExamReader;
    private final InterestCertificateManager interestCertificateManager;
    private final EventPublisher eventPublisher;

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
        getTargetUsers(certificateExam).forEach(user -> CompletableFuture.runAsync(() -> {
            InterestCertificateExamScheduleEvent event = InterestCertificateExamScheduleEvent.of(
                    certificateExam,
                    user,
                    scheduleCheckType
            );
            eventPublisher.publish(event);
        }));
    }

    private List<User> getTargetUsers(CertificateExam certificateExam) {
        return interestCertificateManager.find(certificateExam.certificate()).stream()
                .map(InterestCertificate::user)
                .toList();
    }
}
