package com.cos.cercat.repository;

import static org.assertj.core.api.Assertions.*;

import com.cos.cercat.RepositoryTest;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateRepository;
import com.cos.cercat.domain.certificate.SubjectFixture;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.learning.GoalFixture;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamFixture;
import com.cos.cercat.domain.mockexam.MockExamRepository;
import com.cos.cercat.domain.mockexam.NewMockExam;
import com.cos.cercat.domain.mockexam.NewQuestion;
import com.cos.cercat.domain.mockexam.QuestionFixture;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.mockexamresult.MockExamResultRepository;
import com.cos.cercat.domain.mockexamresult.NewMockExamResult;
import com.cos.cercat.domain.mockexamresult.NewMockExamResultFixture;
import com.cos.cercat.domain.mockexamresult.SubjectResultStatistics;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserFixture;
import com.cos.cercat.domain.user.UserInfo;
import com.cos.cercat.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MockExamResultRepositoryTest extends RepositoryTest {

  @Autowired
  private MockExamResultRepository mockExamResultRepository;

  @Autowired
  private MockExamRepository mockExamRepository;

  @Autowired
  private CertificateRepository certificateRepository;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  public void setup() {
    // Given: 필요한 모든 데이터를 저장합니다.
    UserInfo userInfo = UserFixture.createUserInfo();
    User user = userRepository.save(userInfo);

    certificateRepository.save("testCertificate", SubjectFixture.createInfos());

    NewMockExam newMockExam = MockExamFixture.createNewMockExam();
    mockExamRepository.save(newMockExam);

    List<NewQuestion> newQuestions = QuestionFixture.createNewQuestions(
        SubjectFixture.createSubject());

    mockExamRepository.saveQuestions(TargetMockExam.from(1L), newQuestions);

    MockExam mockExam = mockExamRepository.find(TargetMockExam.from(1L));

    NewMockExamResult newMockExamResult = NewMockExamResultFixture.createNewMockExamResult();
    mockExamResultRepository.save(user, mockExam, newMockExamResult);
  }


  @Test
  @DisplayName("유저와 자격증 그리고 목표기간으로 기간동안의 과목별 평균 데이터 통계를 구할수 있다.")
  public void subjectResultStatisticsTest() {
    //Given
    User user = userRepository.find(TargetUser.from(1L));
    Certificate certificate = certificateRepository.findById(TargetCertificate.from(1L));
    GoalPeriod goalPeriod = GoalFixture.createGoalPeriod();

    //When
    List<SubjectResultStatistics> subjectResultStatistics = mockExamResultRepository.getSubjectResultStatistics(
        user, certificate, goalPeriod);

    //Then
    assertThat(subjectResultStatistics).hasSize(5);
    assertThat(subjectResultStatistics.get(0).correctRate()).isEqualTo(33);
    assertThat(subjectResultStatistics.get(0).totalTakenTime()).isEqualTo(1000L);
  }
}