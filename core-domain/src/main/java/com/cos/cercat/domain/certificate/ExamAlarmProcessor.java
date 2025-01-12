package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.alarm.AlarmNotifier;
import com.cos.cercat.domain.alarm.AlarmType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamAlarmProcessor {

    private final CertificateExamReader certificateExamReader;
    private final InterestCertificateManager interestCertificateManager;
    private final AlarmNotifier alarmNotifier;

    public void process(AlarmType alarmType) {
        switch (alarmType) {
            case BEFORE_APPLICATION -> processBeforeApplicationAlarm();
            case START_APPLICATION -> processApplicationStartedAlarm();
            case DEADLINE -> processBeforeDeadlineAlarm();
            default -> throw new IllegalArgumentException("Unexpected value: " + alarmType);
        }
    }

    private void processBeforeApplicationAlarm() {
        List<CertificateExam> certificateExams = certificateExamReader.read(
                ExamScheduleType.APPLICATION_START, getTomorrowDate());

        int count = 0;

        for (CertificateExam certificateExam : certificateExams) {
            Certificate certificate = certificateExam.certificate();
        }

    }

    private void processApplicationStartedAlarm() {
        List<CertificateExam> certificateExams = certificateExamReader.read(
                ExamScheduleType.APPLICATION_START, getTodayDate());
    }

    private void processBeforeDeadlineAlarm() {
        List<CertificateExam> certificateExams = certificateExamReader.read(ExamScheduleType.DEADLINE, getTomorrowDate());
    }

    private LocalDateTime getTodayDate() {
        return LocalDateTime.now();
    }

    private LocalDateTime getTomorrowDate() {
        return LocalDateTime.now().plusDays(1);
    }


}
