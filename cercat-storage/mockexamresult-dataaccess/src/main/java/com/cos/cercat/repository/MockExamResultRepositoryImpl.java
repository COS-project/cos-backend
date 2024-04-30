package com.cos.cercat.repository;


import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.common.util.DateUtils;
import com.cos.cercat.domain.*;
import com.cos.cercat.dto.DailyScoreAverage;
import com.cos.cercat.dto.MonthlyScoreAverage;
import com.cos.cercat.dto.SubjectResultsAVG;
import com.cos.cercat.dto.WeeklyScoreAverage;
import com.cos.cercat.learning.GoalPeriod;
import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.mockexamresult.*;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Transactional
public class MockExamResultRepositoryImpl implements MockExamResultRepository {

    private final MockExamResultJpaRepository mockExamResultJpaRepository;
    private final SubjectResultJpaRepository subjectResultJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final UserAnswerJpaRepository userAnswerJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;

    @Override
    public TargetMockExamResult save(User user, MockExam mockExam, NewMockExamResult newMockExamResult) {
        UserEntity userEntity = UserEntity.from(user);
        MockExamEntity mockExamEntity = MockExamEntity.from(mockExam);

        MockExamResultEntity mockExamResultEntity = MockExamResultEntity.builder()
                .mockExamEntity(mockExamEntity)
                .userEntity(userEntity)
                .totalScore(newMockExamResult.totalScore())
                .round(newMockExamResult.round())
                .build();

        MockExamResultEntity savedResult = mockExamResultJpaRepository.save(mockExamResultEntity);

        newMockExamResult.newSubjectResults().forEach(newSubjectResult -> {
            SubjectResultEntity subjectResultEntity = saveSubjectResult(newSubjectResult, savedResult);
            newSubjectResult.newUserAnswers().forEach(newUserAnswer -> saveUserAnswer(newUserAnswer, subjectResultEntity, userEntity));
        });

        return TargetMockExamResult.from(savedResult.getId());
    }

    @Override
    public int countMockExamResult(TargetUser targetUser, TargetMockExam targetMockExam) {
        return mockExamResultJpaRepository.countMockExamResults(targetUser.userId(), targetMockExam.mockExamId());
    }

    @Override
    @Transactional(readOnly = true)
    public UserAnswer findUserAnswer(TargetUserAnswer targetUserAnswer) {
        UserAnswerEntity userAnswerEntity = userAnswerJpaRepository.findById(targetUserAnswer.userAnswerId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_ANSWER_NOT_FOUND));
        return userAnswerEntity.toDomain();
    }

    @Override
    public void update(UserAnswer userAnswer) {
        SubjectResultEntity subjectResultEntity = subjectResultJpaRepository.getReferenceById(userAnswer.getSubjectResultId());
        userAnswerJpaRepository.save(UserAnswerEntity.from(userAnswer, subjectResultEntity));
    }

    @Override
    public List<MockExamResultDetail> findMockExamResultDetails(TargetMockExam targetMockExam, TargetUser targetUser) {
        List<MockExamResultEntity> mockExamResultEntities =
                mockExamResultJpaRepository.findByMockExamIdAndUserId(targetMockExam.mockExamId(), targetUser.userId());
        return mockExamResultEntities.stream()
                .map(mockExamResultEntity -> {
                    List<SubjectResultEntity> subjectResultEntities = subjectResultJpaRepository.findByMockExamResultId(mockExamResultEntity.getId());
                    return new MockExamResultDetail(
                            mockExamResultEntity.toDomain(),
                            subjectResultEntities.stream().map(SubjectResultEntity::toDomain).toList()
                    );
                }).toList();
    }

    @Override
    public SliceResult<UserAnswer> findAllWrongUserAnswers(TargetCertificate targetCertificate,
                                                           TargetUser targetUser,
                                                           Cursor cursor) {
        Slice<UserAnswerEntity> userAnswerEntities = userAnswerJpaRepository.findWrongUserAnswersByUserIdAndCertificateId(
                targetUser.userId(),
                targetCertificate.certificateId(),
                toPageRequest(cursor));

        return SliceResult.of(userAnswerEntities.stream().map(UserAnswerEntity::toDomain).toList(), userAnswerEntities.hasNext());

    }

    @Override
    public SliceResult<UserAnswer> findWrongUserAnswers(TargetMockExamResult targetMockExamResult,
                                                        TargetUser targetUser,
                                                        Cursor cursor) {
        Slice<UserAnswerEntity> userAnswerEntities = userAnswerJpaRepository.findWrongUserAnswersByMockExamResultId(
                targetMockExamResult.mockExamResultId(),
                toPageRequest(cursor)
        );

        return SliceResult.of(userAnswerEntities.stream().map(UserAnswerEntity::toDomain).toList(), userAnswerEntities.hasNext());
    }

    @Override
    public MockExamResult find(TargetMockExamResult targetMockExamResult) {
        return mockExamResultJpaRepository.findById(targetMockExamResult.mockExamResultId())
                .orElseThrow(() -> new CustomException(ErrorCode.MOCK_EXAM_RESULT_NOT_FOUND))
                .toDomain();
    }

    @Override
    public PageResult<MockExamResult> findMockExamResultsByDate(TargetUser targetUser,
                                                                TargetCertificate targetCertificate,
                                                                DateCond dateCond,
                                                                Cursor cursor) {
        Page<MockExamResultEntity> mockExamResults = mockExamResultJpaRepository.findMockExamResultsByDate(
                targetUser.userId(),
                targetCertificate.certificateId(),
                dateCond.date(),
                toPageRequest(cursor)
        );

        return PageResult.of(
                mockExamResults.stream()
                        .map(MockExamResultEntity::toDomain)
                        .toList(),
                mockExamResults.getNumber(),
                mockExamResults.getSize()
        );
    }

    @Override
    public PageResult<MockExamResult> findMockExamResultsByWeekOfMonth(TargetUser targetUser,
                                                                       TargetCertificate targetCertificate,
                                                                       DateCond dateCond,
                                                                       Cursor cursor) {
        Page<MockExamResultEntity> mockExamResults = mockExamResultJpaRepository.findMockExamResultsByWeekOfMonth(
                targetUser.userId(),
                targetCertificate.certificateId(),
                DateUtils.getFirstDayOfMonth(LocalDate.of(dateCond.year(), dateCond.month(), 1)),
                dateCond.month(),
                dateCond.weekOfMonth(),
                toPageRequest(cursor)
        );
        return PageResult.of(
                mockExamResults.stream()
                        .map(MockExamResultEntity::toDomain)
                        .toList(),
                mockExamResults.getNumber(),
                mockExamResults.getSize()
        );
    }

    @Override
    public PageResult<MockExamResult> findMockExamResultsByMonth(TargetUser targetUser,
                                                                 TargetCertificate targetCertificate,
                                                                 DateCond dateCond,
                                                                 Cursor cursor) {
        Page<MockExamResultEntity> mockExamResults = mockExamResultJpaRepository.findMockExamResultsByMonth(
                targetUser.userId(),
                targetCertificate.certificateId(),
                dateCond.month(),
                toPageRequest(cursor)
        );
        return PageResult.of(
                mockExamResults.stream()
                        .map(MockExamResultEntity::toDomain)
                        .toList(),
                mockExamResults.getNumber(),
                mockExamResults.getSize()
        );
    }

    @Override
    public List<ScoreData> getDailyScoreData(TargetUser targetUser,
                                             TargetCertificate targetCertificate,
                                             DateCond dateCond) {
        return mockExamResultJpaRepository.getDailyScoreDataList(
                        targetCertificate.certificateId(),
                        targetUser.userId(),
                        dateCond
                ).stream()
                .map(DailyScoreAverage::toScoreData)
                .toList();
    }

    @Override
    public List<ScoreData> getWeeklyScoreData(TargetUser targetUser,
                                              TargetCertificate targetCertificate,
                                              DateCond dateCond) {
        return mockExamResultJpaRepository.getWeeklyScoreDataList(
                        targetCertificate.certificateId(),
                        targetUser.userId(),
                        dateCond
                ).stream()
                .map(WeeklyScoreAverage::toScoreData)
                .toList();
    }

    @Override
    public List<ScoreData> getYearlyScoreData(TargetUser targetUser,
                                              TargetCertificate targetCertificate,
                                              DateCond dateCond) {
        return mockExamResultJpaRepository.getMonthlyScoreDataList(
                        targetCertificate.certificateId(),
                        targetUser.userId(),
                        dateCond
                ).stream()
                .map(MonthlyScoreAverage::toScoreData)
                .toList();
    }

    @Override
    public List<SubjectResultStatistics> getSubjectResultStatistics(User user,
                                                                    Certificate certificate,
                                                                    GoalPeriod goalPeriod) {
        return subjectResultJpaRepository.getSubjectResultsAVG(
                        user.getId(),
                        certificate.id(),
                        goalPeriod.startDateTime(),
                        goalPeriod.endDateTime()
                ).stream()
                .map(SubjectResultsAVG::toDomain)
                .toList();
    }

    @Override
    public int getCurrentMaxScore(TargetCertificate targetCertificate,
                                  TargetUser targetUser,
                                  GoalPeriod goalPeriod) {
        return mockExamResultJpaRepository.getMockExamResultMaxScore(
                targetCertificate.certificateId(),
                targetUser.userId(),
                goalPeriod.startDateTime(),
                goalPeriod.endDateTime()
        );
    }

    @Override
    public int countTodayMockExamResults(TargetCertificate targetCertificate,
                                         TargetUser targetUser) {
        return mockExamResultJpaRepository.countTodayMockExamResults(
                targetCertificate.certificateId(),
                targetUser.userId()
        );
    }

    @Override
    public int countTotalMockExamResults(TargetCertificate targetCertificate,
                                         TargetUser targetUser,
                                         GoalPeriod goalPeriod) {
        return mockExamResultJpaRepository.countTotalMockExamResults(
                targetCertificate.certificateId(),
                targetUser.userId(),
                goalPeriod.startDateTime(),
                goalPeriod.endDateTime()
        );
    }

    @Override
    public MockExamResult readRecent(MockExam mockExam, User user) {
        return mockExamResultJpaRepository.findMockExamResultByMockExamIdAndUserId(mockExam.id(), user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MOCK_EXAM_RESULT_NOT_FOUND))
                .toDomain();
    }

    private SubjectResultEntity saveSubjectResult(NewSubjectResult newSubjectResult, MockExamResultEntity savedResult) {
        SubjectEntity subjectEntity = subjectJpaRepository.getReferenceById(newSubjectResult.subjectId());
        SubjectResultEntity subjectResultEntity = SubjectResultEntity.builder()
                .mockExamResultEntity(savedResult)
                .numberOfCorrect(newSubjectResult.numberOfCorrect())
                .totalTakenTime(newSubjectResult.totalTakenTime())
                .correctRate((int) ((double) newSubjectResult.numberOfCorrect() / subjectEntity.getNumberOfQuestions() * 100))
                .subjectEntity(subjectEntity)
                .score(newSubjectResult.score())
                .build();
        subjectResultJpaRepository.save(subjectResultEntity);
        return subjectResultEntity;
    }

    private void saveUserAnswer(NewUserAnswer newUserAnswer, SubjectResultEntity subjectResultEntity, UserEntity userEntity) {
        UserAnswerEntity userAnswerEntity = UserAnswerEntity.builder()
                .subjectResultEntity(subjectResultEntity)
                .questionEntity(questionJpaRepository.getReferenceById(newUserAnswer.questionId()))
                .userEntity(userEntity)
                .isCorrect(newUserAnswer.isCorrect())
                .selectOptionSeq(newUserAnswer.selectOptionSeq())
                .takenTime(newUserAnswer.takenTime())
                .build();
        userAnswerJpaRepository.save(userAnswerEntity);
    }

    private PageRequest toPageRequest(Cursor cursor) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(cursor.sortDirection().name()).orElseThrow();
        return PageRequest.of(cursor.page(), cursor.size(), Sort.by(direction, cursor.sortKey()));
    }
}
