package com.cos.cercat.repository;

import com.cos.cercat.domain.MockExamResult;
import com.cos.cercat.domain.SubjectResult;
import com.cos.cercat.domain.UserAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MockExamResultBatchRepositoryImpl implements MockExamResultBatchRepository{

    private final JdbcTemplate jdbcTemplate;


    @Override
    public long batchInsert(MockExamResult mockExamResult) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        // MockExamResult 저장 및 생성된 ID 반환
        String mockExamResultSql = "INSERT INTO mock_exam_result (mock_exam_id, user_id, round, total_score, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder mockExamResultKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(mockExamResultSql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, mockExamResult.getMockExam().getId());
            ps.setLong(2, mockExamResult.getUser().getId());
            ps.setInt(3, mockExamResult.getRound());
            ps.setInt(4, mockExamResult.getTotalScore());
            ps.setTimestamp(5, now);
            ps.setTimestamp(6, now);
            return ps;
        }, mockExamResultKeyHolder);
        long mockExamResultId = mockExamResultKeyHolder.getKey().longValue();

        // SubjectResult 저장 및 생성된 ID 반환
        String subjectResultSql = "INSERT INTO subject_result (mock_exam_result_id, subject_id, score, number_of_correct, total_taken_time, correct_rate) VALUES (?, ?, ?, ?, ?, ?)";
        for (SubjectResult subjectResult : mockExamResult.getSubjectResults().getSubjectResults()) {
            KeyHolder subjectResultKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(subjectResultSql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, mockExamResultId);
                ps.setLong(2, subjectResult.getSubject().getId());
                ps.setInt(3, subjectResult.getScore());
                ps.setInt(4, subjectResult.getNumberOfCorrect());
                ps.setLong(5, subjectResult.getTotalTakenTime());
                ps.setInt(6, subjectResult.getCorrectRate());
                return ps;
            }, subjectResultKeyHolder);
            long subjectResultId = subjectResultKeyHolder.getKey().longValue();

            // UserAnswer 저장
            String userAnswerSql = "INSERT INTO user_answer (subject_result_id, question_id, user_id, select_option_seq, taken_time, is_correct, is_reviewed) VALUES (?, ?, ?, ?, ?, ?, ?)";
            for (UserAnswer userAnswer : subjectResult.getUserAnswers().getUserAnswers()) {
                jdbcTemplate.update(userAnswerSql,
                        subjectResultId,
                        userAnswer.getQuestion().getId(),
                        userAnswer.getUser().getId(),
                        userAnswer.getSelectOptionSeq(),
                        userAnswer.getTakenTime(),
                        userAnswer.isCorrect(),
                        userAnswer.isReviewed());
            }
        }
        return mockExamResultId;
    }
}


