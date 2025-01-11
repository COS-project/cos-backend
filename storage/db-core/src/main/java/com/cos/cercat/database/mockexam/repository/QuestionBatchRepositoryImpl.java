package com.cos.cercat.database.mockexam.repository;

import com.cos.cercat.database.mockexam.entity.QuestionEntity;
import com.cos.cercat.database.mockexam.entity.QuestionOptionEntity;
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
    public void batchInsert(List<QuestionEntity> questionEntities) {
        for (QuestionEntity questionEntity : questionEntities) {
            // QuestionContent 저장 및 생성된 ID 반환
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO question (mock_exam_id, subject_id, question_seq, question_text, correct_option, score) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, questionEntity.getMockExamEntity().getId());
                ps.setLong(2, questionEntity.getSubjectEntity().getId());
                ps.setInt(3, questionEntity.getQuestionSeq());
                ps.setString(4, questionEntity.getQuestionText());
                ps.setInt(5, questionEntity.getCorrectOption());
                ps.setInt(6, questionEntity.getScore());
                return ps;
            }, keyHolder);

            Long questionId = keyHolder.getKey().longValue();

            // QuestionOptionEntity 배치 삽입
            List<QuestionOptionEntity> options = questionEntity.getQuestionOptions();
            String optionSql = "INSERT INTO question_option (question_id, option_sequence, option_content) VALUES (?, ?, ?)";
            jdbcTemplate.batchUpdate(optionSql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    QuestionOptionEntity option = options.get(i);
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
