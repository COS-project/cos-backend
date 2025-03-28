package com.cos.cercat.database.post.entity;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.mockexam.entity.QuestionEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.post.CommentaryPost;
import com.cos.cercat.domain.post.DateTime;
import com.cos.cercat.domain.post.PostContent;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "commentary_post")
@DiscriminatorValue("CommentaryPost")
public class CommentaryPostEntity extends PostEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @OnDelete(action =  OnDeleteAction.CASCADE)
    private QuestionEntity questionEntity;

    public static CommentaryPostEntity from(CommentaryPost commentaryPost) {
        return CommentaryPostEntity.builder()
                .id(commentaryPost.getId())
                .postType(commentaryPost.getType())
                .title(commentaryPost.getPostContent().title())
                .content(commentaryPost.getPostContent().content())
                .userEntity(UserEntity.from(commentaryPost.getWriter()))
                .certificateEntity(CertificateEntity.from(commentaryPost.getCertificate()))
                .questionEntity(QuestionEntity.from(commentaryPost.getQuestion()))
                .createdAt(commentaryPost.getDateTime().createdAt())
                .modifiedAt(commentaryPost.getDateTime().modifiedAt())
                .build();
    }

    public CommentaryPost toDomain(List<Image> images) {
        return CommentaryPost.builder()
                .id(id)
                .type(postType)
                .postContent(new PostContent(title, content))
                .writer(userEntity.toDomain())
                .certificate(certificateEntity.toDomain())
                .question(questionEntity.toDomain())
                .dateTime(new DateTime(createdAt, modifiedAt))
                .commentCount(commentCount)
                .likeCount(likeCount)
                .postImages(images)
                .build();
    }

}
