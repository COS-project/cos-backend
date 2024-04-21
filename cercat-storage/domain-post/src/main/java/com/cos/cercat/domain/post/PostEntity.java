package com.cos.cercat.domain.post;

import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostComments;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorColumn
@Table(name = "post")
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;

    protected String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    protected CertificateEntity certificateEntity;

    @Embedded
    protected PostImages postImages = new PostImages();

    @Embedded
    protected PostComments postComments = new PostComments();

    @Enumerated(EnumType.STRING)
    protected PostType postType;

    @ColumnDefault("0")
    protected Integer likeCount = 0;

    public PostEntity(String title, String content, UserEntity userEntity, CertificateEntity certificateEntity, PostType postType, List<Image> images) {
        this.title = title;
        this.content = content;
        this.userEntity = userEntity;
        this.certificateEntity = certificateEntity;
        this.postType = postType;
        addAllPostImages(images);
    }

    public PostEntity(String title, String content, UserEntity userEntity, CertificateEntity certificateEntity, PostType postType) {
        this.title = title;
        this.content = content;
        this.userEntity = userEntity;
        this.certificateEntity = certificateEntity;
        this.postType = postType;
    }

    public Post toDomain(List<String> postImageUrls, int commentCount, Set<RecommendTag> recommendTags) {
        return switch (postType) {
            case COMMENTARY ->
                new CommentaryPost(
                    id,
                    userEntity.toDomain(),
                    new PostContent(title, content, postType, postImageUrls),
                    new PostStatus(likeCount, commentCount),
                    ((CommentaryPostEntity) this).getQuestionEntity().toDomain(),
                    new DateTime(createdAt, modifiedAt)
                );
            case TIP -> new TipPost(
                    id,
                    userEntity.toDomain(),
                    new PostContent(title, content, postType, postImageUrls),
                    new PostStatus(likeCount, commentCount),
                    new DateTime(createdAt, modifiedAt),
                    recommendTags
            );
            default -> new Post(
                    id,
                    userEntity.toDomain(),
                    new PostContent(title, content, postType, postImageUrls),
                    new PostStatus(likeCount, commentCount),
                    new DateTime(createdAt, modifiedAt)
            );
        };
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void addComment(PostCommentEntity comment) {
        comment.setPostEntity(this);
        this.getPostComments().addComment(comment);
    }

    public void updatePostInfo(String title, String content, List<Image> images) {
        if (Objects.nonNull(title)) this.title = title;
        if (Objects.nonNull(content)) this.content = content;
        addAllPostImages(images);
    }

    public boolean isAuthorized(UserEntity userEntity) {
        return this.userEntity.equals(userEntity);
    }

    private void addAllPostImages(List<Image> images) {
        List<PostImageEntity> postImageEntities = images.stream()
                .map(image -> PostImageEntity.of(this, image))
                .toList();

        this.postImages.addAll(postImageEntities);
    }
}
