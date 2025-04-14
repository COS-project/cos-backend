package com.cos.cercat.certificate;

import static com.cos.cercat.utils.FixtureFactory.MONKEY;
import static org.assertj.core.api.Assertions.assertThat;

import com.cos.cercat.domain.certificate.CertificateAppender;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.CertificateExamReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.certificate.ExamSchedule;
import com.cos.cercat.domain.certificate.ExamScheduleType;
import com.cos.cercat.domain.certificate.ExamTimeLimit;
import com.cos.cercat.domain.certificate.InterestCertificateManager;
import com.cos.cercat.domain.certificate.InterestPriority;
import com.cos.cercat.domain.certificate.InterestTarget;
import com.cos.cercat.domain.certificate.InterestTargets;
import com.cos.cercat.domain.certificate.PassingCriteria;
import com.cos.cercat.domain.certificate.SubjectInfo;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserAppender;
import com.cos.cercat.domain.user.UserInfo;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateExamAppender;
import com.cos.cercat.domain.certificate.CertificateExamScheduleChecker;
import com.cos.cercat.domain.certificate.ExamFee;
import com.cos.cercat.domain.certificate.NewExamInformation;
import com.cos.cercat.domain.common.event.outbox.OutboxEvent;
import com.cos.cercat.domain.common.event.outbox.OutboxEventManager;
import java.util.List;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles({"test", "api"})
@Sql(statements = "CREATE ALIAS IF NOT EXISTS date_format FOR \"com.cos.cercat.utils.H2CustomAlias.date_format\"", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CertificateExamScheduleCheckerIntegrationTest {

    @Autowired
    private CertificateExamScheduleChecker checker;

    @Autowired
    private CertificateExamAppender certificateExamAppender;

    @Autowired
    private CertificateAppender certificateAppender;

    @Autowired
    private OutboxEventManager outboxEventManager;

    @Autowired
    private UserAppender userAppender;

    @Autowired
    private InterestCertificateManager interestCertificateManager;

    @Test
    @DisplayName("내일 일정의 CertificateExam이 존재할 때, 자격증 스케줄 이벤트가 Outbox에 저장된다")
    void shouldStoreEventInOutboxWhenTomorrowExamExists() {
        // given
        Certificate certificate = createCertificate();
        User user = createTestUser();
        registerUserInterestInCertificate(user, certificate);
        NewExamInformation examInfo = createExamInformation();

        certificateExamAppender.append(certificate, examInfo);

        // when
        checker.check();

        // then
        List<OutboxEvent> events = outboxEventManager.readUnprocessedEvents();

        assertThat(events).isNotEmpty();
    }

    private Certificate createCertificate() {
        Certificate certificate = new Certificate(new CertificateId(1L), "정보처리기사");
        List<SubjectInfo> subjects = MONKEY.giveMe(SubjectInfo.class, 2);
        certificateAppender.append(certificate.certificateName(), subjects);
        return certificate;
    }

    private User createTestUser() {
        return userAppender.append(new UserInfo("testUser", "testPassword", "testEmail"));
    }

    private void registerUserInterestInCertificate(User user, Certificate certificate) {
        InterestTarget interestTarget = new InterestTarget(certificate.id().value(), InterestPriority.LOW);
        InterestTargets interestTargets = InterestTargets.from(List.of(interestTarget));
        interestCertificateManager.append(user, interestTargets);
    }

    private ExamSchedule createExamSchedule() {
        return MONKEY.giveMeBuilder(ExamSchedule.class)
                .set("applicationStartDateTime", LocalDateTime.now().plusDays(1))
                .set("applicationDeadlineDateTime", LocalDateTime.now().plusDays(2))
                .set("examDateTime", LocalDateTime.now().plusDays(3))
                .set("resultAnnouncementDateTime", LocalDateTime.now().plusDays(4))
                .sample();
    }

    private NewExamInformation createExamInformation() {
        return MONKEY.giveMeBuilder(NewExamInformation.class)
                .set("examSchedule", createExamSchedule())
                .set("examTimeLimit", MONKEY.giveMeOne(ExamTimeLimit.class))
                .set("examFee", MONKEY.giveMeOne(ExamFee.class))
                .set("passingCriteria", MONKEY.giveMeOne(PassingCriteria.class))
                .set("subjectsInfo", "정보처리기사 과목 내용")
                .set("description", "자격증 설명")
                .set("examFormat", "필기+실기")
                .set("examEligibility", "제한 없음")
                .sample();
    }
}
