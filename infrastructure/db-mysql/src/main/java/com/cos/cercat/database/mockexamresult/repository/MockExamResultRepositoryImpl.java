package com.cos.cercat.database.mockexamresult.repository;


import com.cos.cercat.database.certificate.entity.SubjectEntity;
import com.cos.cercat.database.certificate.repository.SubjectJpaRepository;
import com.cos.cercat.database.mockexam.entity.MockExamEntity;
import com.cos.cercat.database.mockexam.repository.QuestionJpaRepository;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.PageResult;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.database.common.util.DateUtils;
import com.cos.cercat.domain.mockexamresult.*;
import com.cos.cercat.database.mockexamresult.dto.DailyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.MonthlyScoreAverage;
import com.cos.cercat.database.mockexamresult.dto.SubjectResultsAVG;
import com.cos.cercat.database.mockexamresult.dto.WeeklyScoreAverage;
import com.cos.cercat.domain.mockexamresult.exception.MockExamResultNotFoundException;
import com.cos.cercat.domain.mockexamresult.exception.UserAnswerNotFoundException;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.mockexam.MockExam;

import com.cos.cercat.domain.user.User;
import com.cos.cercat.database.mockexamresult.entity.MockExamResultEntity;
import com.cos.cercat.database.mockexamresult.entity.SubjectResultEntity;
import com.cos.cercat.database.mockexamresult.entity.UserAnswerEntity;
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
    public MockExamResultId save(User user, MockExam mockExam,
            NewMockExamResult newMockExamResult) {
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
            SubjectResultEntity subjectResultEntity = saveSubjectResult(newSubjectResult,
                    savedResult);
            newSubjectResult.newUserAnswers().forEach(
                    newUserAnswer -> saveUserAnswer(newUserAnswer, subjectResultEntity,
                            userEntity));
        });

        return MockExamResultId.from(savedResult.getId());
    }

    @Override
    public int countMockExamResult(User user, MockExam mockExam) {
        return mockExamResultJpaRepository.countMockExamResults(user.getId(), mockExam.id());
    }

    @Override
    @Transactional(readOnly = true)
    public UserAnswer findUserAnswer(UserAnswerId userAnswerId) {
        UserAnswerEntity userAnswerEntity = userAnswerJpaRepository.findById(
                userAnswerId.userAnswerId()).orElseThrow(
                () -> UserAnswerNotFoundException.EXCEPTION);
        return userAnswerEntity.toDomain();
    }

    @Override
    public void update(UserAnswer userAnswer) {
        userAnswerJpaRepository.save(UserAnswerEntity.from(userAnswer));
    }

    @Override
    public List<MockExamResultDetail> findMockExamResultDetails(User user, MockExam mockExam) {
        List<MockExamResultEntity> mockExamResultEntities =
                mockExamResultJpaRepository.findByMockExamIdAndUserId(user.getId(), mockExam.id());
        return mockExamResultEntities.stream()
                .map(mockExamResultEntity -> {
                    List<SubjectResultEntity> subjectResultEntities = subjectResultJpaRepository.findByMockExamResultId(
                            mockExamResultEntity.getId());
                    return new MockExamResultDetail(
                            mockExamResultEntity.toDomain(),
                            subjectResultEntities.stream().map(SubjectResultEntity::toDomain)
                                    .toList()
                    );
                }).toList();
    }

    @Override
    public SliceResult<UserAnswer> findAllWrongUserAnswers(User user,
            Certificate certificate,
            Cursor cursor) {
        Slice<UserAnswerEntity> userAnswerEntities = userAnswerJpaRepository.findWrongUserAnswersByUserIdAndCertificateId(
                user.getId(),
                certificate.id().value(),
                toPageRequest(cursor));

        return SliceResult.of(userAnswerEntities.stream().map(UserAnswerEntity::toDomain).toList(),
                userAnswerEntities.hasNext());

    }

    @Override
    public SliceResult<UserAnswer> findWrongUserAnswers(MockExamResult mockExamResult,
            Cursor cursor) {
        Slice<UserAnswerEntity> userAnswerEntities = userAnswerJpaRepository.findWrongUserAnswersByMockExamResultId(
                mockExamResult.getId(),
                toPageRequest(cursor)
        );

        return SliceResult.of(userAnswerEntities.stream().map(UserAnswerEntity::toDomain).toList(),
                userAnswerEntities.hasNext());
    }

    @Override
    public MockExamResult find(MockExamResultId mockExamResultId) {
        return mockExamResultJpaRepository.findById(mockExamResultId.mockExamResultId())
                .orElseThrow(() -> MockExamResultNotFoundException.EXCEPTION)
                .toDomain();
    }

    @Override
    public PageResult<MockExamResult> findMockExamResultsByDate(User user,
            Certificate certificate,
            DateCond dateCond,
            Cursor cursor) {
        Page<MockExamResultEntity> mockExamResults = mockExamResultJpaRepository.findMockExamResultsByDate(
                user.getId(),
                certificate.id().value(),
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
    public PageResult<MockExamResult> findMockExamResultsByWeekOfMonth(User user,
            Certificate certificate,
            DateCond dateCond,
            Cursor cursor) {
        Page<MockExamResultEntity> mockExamResults = mockExamResultJpaRepository.findMockExamResultsByWeekOfMonth(
                user.getId(),
                certificate.id().value(),
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
    public PageResult<MockExamResult> findMockExamResultsByMonth(User user,
            Certificate certificate,
            DateCond dateCond,
            Cursor cursor) {
        Page<MockExamResultEntity> mockExamResults = mockExamResultJpaRepository.findMockExamResultsByMonth(
                user.getId(),
                certificate.id().value(),
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
    public List<ScoreData> getDailyScoreData(User user,
            Certificate certificate,
            DateCond dateCond) {
        return mockExamResultJpaRepository.getDailyScoreDataList(
                        user.getId(),
                        certificate.id().value(),
                        dateCond
                ).stream()
                .map(DailyScoreAverage::toScoreData)
                .toList();
    }

    @Override
    public List<ScoreData> getWeeklyScoreData(User user,
            Certificate certificate,
            DateCond dateCond) {
        return mockExamResultJpaRepository.getWeeklyScoreDataList(
                        user.getId(),
                        certificate.id().value(),
                        dateCond
                ).stream()
                .map(WeeklyScoreAverage::toScoreData)
                .toList();
    }

    @Override
    public List<ScoreData> getYearlyScoreData(User user,
            Certificate certificate,
            DateCond dateCond) {
        return mockExamResultJpaRepository.getMonthlyScoreDataList(
                        user.getId(),
                        certificate.id().value(),
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
                        certificate.id().value(),
                        goalPeriod.startDateTime(),
                        goalPeriod.endDateTime()
                ).stream()
                .map(SubjectResultsAVG::toDomain)
                .toList();
    }

    @Override
    public int getCurrentMaxScore(User user,
            Certificate certificate,
            GoalPeriod goalPeriod) {
        Integer currentMax = mockExamResultJpaRepository.getMockExamResultMaxScore(
                user.getId(),
                certificate.id().value(),
                goalPeriod.startDateTime(),
                goalPeriod.endDateTime()
        );
        return currentMax == null ? 0 : currentMax;
    }

    @Override
    public int countTodayMockExamResults(User user,
            Certificate certificate) {
        return mockExamResultJpaRepository.countTodayMockExamResults(
                user.getId(),
                certificate.id().value()
        );
    }

    @Override
    public int countTotalMockExamResults(User user,
            Certificate certificate,
            GoalPeriod goalPeriod) {
        return mockExamResultJpaRepository.countTotalMockExamResults(
                user.getId(),
                certificate.id().value(),
                goalPeriod.startDateTime(),
                goalPeriod.endDateTime()
        );
    }

    @Override
    public MockExamResult readRecent(MockExam mockExam, User user) {
        return mockExamResultJpaRepository.findMockExamResultByMockExamIdAndUserId(mockExam.id(),
                        user.getId())
                .orElseThrow(() -> MockExamResultNotFoundException.EXCEPTION)
                .toDomain();
    }

    private SubjectResultEntity saveSubjectResult(NewSubjectResult newSubjectResult,
            MockExamResultEntity savedResult) {
        SubjectEntity subjectEntity = subjectJpaRepository.getReferenceById(
                newSubjectResult.subjectId());
        SubjectResultEntity subjectResultEntity = SubjectResultEntity.builder()
                .mockExamResultEntity(savedResult)
                .numberOfCorrect(newSubjectResult.numberOfCorrect())
                .totalTakenTime(newSubjectResult.totalTakenTime())
                .correctRate((int) ((double) newSubjectResult.numberOfCorrect()
                        / subjectEntity.getNumberOfQuestions() * 100))
                .subjectEntity(subjectEntity)
                .score(newSubjectResult.score())
                .build();
        subjectResultJpaRepository.save(subjectResultEntity);
        return subjectResultEntity;
    }

    private void saveUserAnswer(NewUserAnswer newUserAnswer,
            SubjectResultEntity subjectResultEntity, UserEntity userEntity) {
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
        // 여러 정렬 기준을 처리할 Sort 객체 생성
        Sort sort = Sort.by(cursor.sortOrders().stream()
                .map(order -> new Sort.Order(
                        Sort.Direction.fromString(order.direction().name()),
                        order.key()
                ))
                .toList());

        return PageRequest.of(cursor.page(), cursor.size(), sort);
    }
}
