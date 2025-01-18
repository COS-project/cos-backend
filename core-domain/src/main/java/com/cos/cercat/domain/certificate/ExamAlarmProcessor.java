package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.alarm.AlarmNotificationService;
import com.cos.cercat.domain.alarm.AlarmSchedule;
import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.alarm.ExamAlarm;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExamAlarmProcessor {

    private final CertificateExamReader certificateExamReader;
    private final InterestCertificateManager interestCertificateManager;
    private final AlarmNotificationService alarmNotificationService;

    public void process() {
        for (AlarmSchedule alarmSchedule : AlarmSchedule.values()) {
            int count = processExamAlarm(alarmSchedule);
            log.info("[{} 알림] {} 건 전송 완료", alarmSchedule.name(), count);
        }
    }

    private int processExamAlarm(AlarmSchedule alarmSchedule) {
        List<CertificateExam> certificateExams = certificateExamReader.read(alarmSchedule.getExamScheduleType(), alarmSchedule.getDate());
        int count = 0;
        for (CertificateExam certificateExam : certificateExams) {
            Certificate certificate = certificateExam.certificate();
            List<User> users = getAlarmTargetUsers(certificate);    
            count += notifyToUser(alarmSchedule.getAlarmType(), certificateExam, users);
        }
        return count;
    }

    private int notifyToUser(AlarmType alarmType, CertificateExam certificateExam, List<User> users) {
        AtomicInteger count = new AtomicInteger(0);
        for (User user : users) {
            alarmNotificationService.notify(ExamAlarm.from(certificateExam, user, alarmType));
            count.incrementAndGet();
        }
        return count.get();
    }

    private List<User> getAlarmTargetUsers(Certificate certificate) {
        return interestCertificateManager.find(certificate).stream()
                .map(InterestCertificate::user)
                .toList();
    }
}
