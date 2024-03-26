package com.cos.cercat.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNormalPost is a Querydsl query type for NormalPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNormalPost extends EntityPathBase<NormalPost> {

    private static final long serialVersionUID = -2139142215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNormalPost normalPost = new QNormalPost("normalPost");

    public final QPost _super;

    // inherited
    public final com.cos.cercat.domain.certificate.domain.QCertificate certificate;

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
    public final com.cos.cercat.domain.comment.domain.QPostComments postComments;

    // inherited
    public final QPostImages postImages;

    //inherited
    public final EnumPath<PostType> postType;

    //inherited
    public final StringPath title;

    // inherited
    public final com.cos.cercat.domain.user.domain.QUser user;

    public QNormalPost(String variable) {
        this(NormalPost.class, forVariable(variable), INITS);
    }

    public QNormalPost(Path<? extends NormalPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNormalPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNormalPost(PathMetadata metadata, PathInits inits) {
        this(NormalPost.class, metadata, inits);
    }

    public QNormalPost(Class<? extends NormalPost> type, PathMetadata metadata, PathInits inits) {
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
        this.title = _super.title;
        this.user = _super.user;
    }

}

