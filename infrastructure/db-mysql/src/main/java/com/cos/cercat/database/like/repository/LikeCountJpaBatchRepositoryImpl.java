package com.cos.cercat.database.like.repository;

import com.cos.cercat.domain.like.LikeCount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LikeCountJpaBatchRepositoryImpl implements LikeCountJpaBatchRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private static final int BATCH_SIZE = 1000;

    @Override
    @Transactional
    public void batchUpdate(List<LikeCount> likeCounts) {
        if (likeCounts == null || likeCounts.isEmpty()) return;

        for (int i = 0; i < likeCounts.size(); i += BATCH_SIZE) {
            List<LikeCount> batch = likeCounts.subList(i, Math.min(i + BATCH_SIZE, likeCounts.size()));

            // CASE 문을 사용한 벌크 업데이트
            StringBuilder caseClause = new StringBuilder("UPDATE like_count SET count = count + CASE ");

            for (int j = 0; j < batch.size(); j++) {
                caseClause.append("WHEN (target_type = :type").append(j)
                        .append(" AND target_id = :id").append(j)
                        .append(") THEN :delta").append(j).append(" ");
            }

            // IN 절을 사용한 WHERE 조건 (row constructor 사용)
            StringBuilder whereClause = new StringBuilder("WHERE (target_type, target_id) IN (");

            for (int j = 0; j < batch.size(); j++) {
                if (j > 0) whereClause.append(", ");
                whereClause.append("(:type").append(j).append(", :id").append(j).append(")");
            }
            whereClause.append(")");

            String sql = caseClause + "ELSE 0 END " + whereClause;
            var query = entityManager.createNativeQuery(sql);

            // 파라미터 바인딩
            for (int j = 0; j < batch.size(); j++) {
                LikeCount lc = batch.get(j);
                query.setParameter("type" + j, lc.likeTarget().targetType().name());
                query.setParameter("id" + j, lc.likeTarget().targetId());
                query.setParameter("delta" + j, lc.value());
            }

            query.executeUpdate();
        }

        entityManager.flush();
        entityManager.clear();
    }
}
