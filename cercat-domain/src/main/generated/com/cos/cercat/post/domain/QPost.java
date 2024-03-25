package com.cos.cercat.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -433131408L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.cos.cercat.common.domain.QBaseTimeEntity _super = new com.cos.cercat.common.domain.QBaseTimeEntity(this);

    public final com.cos.cercat.certificate.domain.QCertificate certificate;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.cos.cercat.comment.domain.QPostComments postComments;

    public final QPostImages postImages;

    public final EnumPath<PostType> postType = createEnum("postType", PostType.class);

    public final StringPath title = createString("title");

    public final com.cos.cercat.user.domain.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificate = inits.isInitialized("certificate") ? new com.cos.cercat.certificate.domain.QCertificate(forProperty("certificate"), inits.get("certificate")) : null;
        this.postComments = inits.isInitialized("postComments") ? new com.cos.cercat.comment.domain.QPostComments(forProperty("postComments")) : null;
        this.postImages = inits.isInitialized("postImages") ? new QPostImages(forProperty("postImages")) : null;
        this.user = inits.isInitialized("user") ? new com.cos.cercat.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

