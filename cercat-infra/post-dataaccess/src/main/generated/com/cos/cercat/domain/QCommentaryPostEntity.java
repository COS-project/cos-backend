package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentaryPostEntity is a Querydsl query type for CommentaryPostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentaryPostEntity extends EntityPathBase<CommentaryPostEntity> {

    private static final long serialVersionUID = 1459056290L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentaryPostEntity commentaryPostEntity = new QCommentaryPostEntity("commentaryPostEntity");

    public final QPostEntity _super;

    // inherited
    public final QCertificateEntity certificateEntity;

    //inherited
    public final StringPath content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Integer> likeCount;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final EnumPath<com.cos.cercat.domain.post.PostType> postType;

    public final QQuestionEntity questionEntity;

    //inherited
    public final StringPath title;

    // inherited
    public final QUserEntity userEntity;

    public QCommentaryPostEntity(String variable) {
        this(CommentaryPostEntity.class, forVariable(variable), INITS);
    }

    public QCommentaryPostEntity(Path<? extends CommentaryPostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentaryPostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentaryPostEntity(PathMetadata metadata, PathInits inits) {
        this(CommentaryPostEntity.class, metadata, inits);
    }

    public QCommentaryPostEntity(Class<? extends CommentaryPostEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPostEntity(type, metadata, inits);
        this.certificateEntity = _super.certificateEntity;
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.likeCount = _super.likeCount;
        this.modifiedAt = _super.modifiedAt;
        this.postType = _super.postType;
        this.questionEntity = inits.isInitialized("questionEntity") ? new QQuestionEntity(forProperty("questionEntity"), inits.get("questionEntity")) : null;
        this.title = _super.title;
        this.userEntity = _super.userEntity;
    }

}

