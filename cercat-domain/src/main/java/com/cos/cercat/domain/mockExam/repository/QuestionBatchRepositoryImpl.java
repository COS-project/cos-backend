package com.cos.cercat.domain.mockExam.repository;

import com.cos.cercat.domain.mockExam.domain.Question;
import com.cos.cercat.domain.mockExam.domain.QuestionOption;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionBatchRepositoryImpl implements QuestionBatchRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsert(List<Question> questions) {
        for (Question question : questions) {
            // Question 저장 및 생성된 ID 반환
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO question (mock_exam_id, subject_id, question_seq, question_text, correct_option, score) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, question.getMockExam().getId());
                ps.setLong(2, question.getSubject().getId());
                ps.setInt(3, question.getQuestionSeq());
                ps.setString(4, question.getQuestionText());
                ps.setInt(5, question.getCorrectOption());
                ps.setInt(6, question.getScore());
                return ps;
            }, keyHolder);

            Long questionId = keyHolder.getKey().longValue();

            // QuestionOption 배치 삽입
            List<QuestionOption> options = question.getQuestionOptions().getAll();
            String optionSql = "INSERT INTO question_option (question_id, option_sequence, option_content) VALUES (?, ?, ?)";
            jdbcTemplate.batchUpdate(optionSql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    QuestionOption option = options.get(i);
                    ps.setLong(1, questionId);
                    ps.setInt(2, option.getOptionSequence());
                    ps.setString(3, option.getOptionContent());
                }

                @Override
                public int getBatchSize() {
                    return options.size();
                }
            });
        }
    }
}
