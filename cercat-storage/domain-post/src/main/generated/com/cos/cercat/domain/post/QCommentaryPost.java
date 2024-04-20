package com.cos.cercat.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentaryPost is a Querydsl query type for CommentaryPostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentaryPost extends EntityPathBase<CommentaryPost> {

    private static final long serialVersionUID = 477417217L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentaryPost commentaryPost = new QCommentaryPost("commentaryPost");

    public final QPost _super;

    // inherited
    public final com.cos.cercat.domain.QCertificate certificate;

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

    // inherited
    public final com.cos.cercat.domain.comment.QPostComments postComments;

    // inherited
    public final QPostImages postImages;

    //inherited
    public final EnumPath<PostType> postType;

    public final com.cos.cercat.domain.QQuestion question;

    //inherited
    public final StringPath title;

    // inherited
    public final com.cos.cercat.domain.QUser user;

    public QCommentaryPost(String variable) {
        this(CommentaryPost.class, forVariable(variable), INITS);
    }

    public QCommentaryPost(Path<? extends CommentaryPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentaryPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentaryPost(PathMetadata metadata, PathInits inits) {
        this(CommentaryPost.class, metadata, inits);
    }

    public QCommentaryPost(Class<? extends CommentaryPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPost(type, metadata, inits);
        this.certificate = _super.certificate;
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.likeCount = _super.likeCount;
        this.modifiedAt = _super.modifiedAt;
        this.postComments = _super.postComments;
        this.postImages = _super.postImages;
        this.postType = _super.postType;
        this.question = inits.isInitialized("question") ? new com.cos.cercat.domain.QQuestion(forProperty("question"), inits.get("question")) : null;
        this.title = _super.title;
        this.user = _super.user;
    }

}

