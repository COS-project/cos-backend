package com.cos.cercat.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for PostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1795152312L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    public final com.cos.cercat.domain.QCertificate certificate;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.cos.cercat.domain.comment.QPostComments postComments;

    public final QPostImages postImages;

    public final EnumPath<PostType> postType = createEnum("postType", PostType.class);

    public final StringPath title = createString("title");

    public final com.cos.cercat.domain.QUser user;

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
        this.certificate = inits.isInitialized("certificateEntity") ? new com.cos.cercat.domain.QCertificate(forProperty("certificateEntity"), inits.get("certificateEntity")) : null;
        this.postComments = inits.isInitialized("postComments") ? new com.cos.cercat.domain.comment.QPostComments(forProperty("postComments")) : null;
        this.postImages = inits.isInitialized("postImages") ? new QPostImages(forProperty("postImages")) : null;
        this.user = inits.isInitialized("userEntity") ? new com.cos.cercat.domain.QUser(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

