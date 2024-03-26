package com.cos.cercat.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTipPost is a Querydsl query type for TipPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTipPost extends EntityPathBase<TipPost> {

    private static final long serialVersionUID = 1824264131L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTipPost tipPost = new QTipPost("tipPost");

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

    public final QRecommendTags recommendTags;

    //inherited
    public final StringPath title;

    // inherited
    public final com.cos.cercat.domain.QUser user;

    public QTipPost(String variable) {
        this(TipPost.class, forVariable(variable), INITS);
    }

    public QTipPost(Path<? extends TipPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTipPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTipPost(PathMetadata metadata, PathInits inits) {
        this(TipPost.class, metadata, inits);
    }

    public QTipPost(Class<? extends TipPost> type, PathMetadata metadata, PathInits inits) {
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
        this.recommendTags = inits.isInitialized("recommendTags") ? new QRecommendTags(forProperty("recommendTags")) : null;
        this.title = _super.title;
        this.user = _super.user;
    }

}

