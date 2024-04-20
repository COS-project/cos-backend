package com.cos.cercat.apis.post.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.domain.post.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record PostResponse(
        Long postId,
        PostType postType,
        String title,
        String content,
        UserResponse user,
        String thumbnailImage,
        Integer likeCount,
        boolean isLiked,
        Integer commentCount,
        Integer questionSequence,
        MockExamResponse mockExam,
        LocalDateTime createdAt
) {

    /**
     * 이 메소드는 PostResponse 레코드의 새 인스턴스를 생성하는 데 사용됩니다.
     * 이 메소드는 PostEntity 객체와 게시물이 유저가 좋아요를 했는지 나타내는 boolean 값을 매개변수로 받습니다.
     * 이 메소드는 PostEntity 객체에서 필요한 정보를 추출하고 이를 사용하여 PostResponse 레코드를 초기화합니다.
     * 만약 PostEntity 객체가 CommentaryPost의 인스턴스라면, 문제번호와 모의고사 정보도 추출합니다.
     *
     * @param postEntity 정보를 추출할 PostEntity 객체.
     * @param isLiked 게시물을 좋아요했는지 나타내는 boolean 값.
     * @return PostResponse 레코드의 새 인스턴스.
     */
    public static PostResponse of(PostEntity postEntity, boolean isLiked) {
        return new PostResponse(
                postEntity.getId(),
                postEntity.getPostType(),
                postEntity.getTitle(),
                postEntity.getContent(),
                UserResponse.fromEntity(postEntity.getUserEntity()),
                postEntity.getPostImages().getThumbnailImageUrl(),
                postEntity.getLikeCount(),
                isLiked,
                postEntity.getPostComments().countComments(),
                postEntity instanceof CommentaryPostEntity commentaryPost? commentaryPost.getQuestionEntity().getQuestionSeq() : null,
                postEntity instanceof CommentaryPostEntity commentaryPost? MockExamResponse.from(commentaryPost.getQuestionEntity().getMockExamEntity()) : null,
                postEntity.getCreatedAt()
        );
    }

}
