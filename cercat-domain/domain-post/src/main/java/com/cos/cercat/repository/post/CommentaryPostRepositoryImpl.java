package com.cos.cercat.repository.post;

import com.cos.cercat.domain.*;
import com.cos.cercat.domain.post.CommentaryPost;
import com.cos.cercat.domain.post.QCommentaryPost;
import com.cos.cercat.dto.CommentaryPostSearchCond;
import com.cos.cercat.util.PagingUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@RequiredArgsConstructor
public class CommentaryPostRepositoryImpl implements PostRepositoryCustom<CommentaryPost> {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<CommentaryPost> searchPosts(Pageable pageable, Certificate certificate, CommentaryPostSearchCond cond) {

        List<CommentaryPost> contents = queryFactory
                .selectFrom(QCommentaryPost.commentaryPost)
                .leftJoin(QCommentaryPost.commentaryPost.user, QUser.user)
                .fetchJoin()
                .leftJoin(QCommentaryPost.commentaryPost.question, QQuestion.question)
                .fetchJoin()
                .leftJoin(QCommentaryPost.commentaryPost.question.mockExam, QMockExam.mockExam)
                .fetchJoin()
                .leftJoin(QCommentaryPost.commentaryPost.certificate, QCertificate.certificate)
                .fetchJoin()
                .where(
                        eqExamYear(cond.examYear()),
                        eqRound(cond.round()),
                        eqQuestionSequence(cond.questionSequence()),
                        QCommentaryPost.commentaryPost.certificate.eq(certificate)
                )
                .orderBy(postSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }


    private BooleanExpression eqExamYear(Integer examYear) {
        if (examYear == null) return null;


        return QCommentaryPost.commentaryPost.question.mockExam.examYear.eq(examYear);
    }

    private BooleanExpression eqRound(Integer round) {
        if (round == null) return null;

        return QCommentaryPost.commentaryPost.question.mockExam.round.eq(round);
    }

    private BooleanExpression eqQuestionSequence(Integer questionSequence) {
        if (questionSequence == null) return null;

        return QCommentaryPost.commentaryPost.question.questionSeq.eq(questionSequence);
    }

    public static OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable, QCommentaryPost.commentaryPost.createdAt, QCommentaryPost.commentaryPost.likeCount);
    }
}
