package com.cos.cercat.post;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostAppender {

    private final CreatePostRepository postRepository;

    @Transactional
    public TargetPost appendCommentaryPost(TargetUser targetUser,
                                     TargetCertificate targetCertificate,
                                     PostContent postContent,
                                     Question question) {
        return postRepository.saveCommentaryPost(targetUser, targetCertificate, postContent, question);

    }

    @Transactional
    public TargetPost appendNormalPost(TargetUser targetUser,
                                       TargetCertificate targetCertificate,
                                       PostContent postContent) {
        return postRepository.saveNormalPost(targetUser, targetCertificate, postContent);
    }

    @Transactional
    public TargetPost appendTipPost(TargetUser targetUser,
                              TargetCertificate targetCertificate,
                              PostContent postContent,
                              Set<RecommendTag> recommendTags) {
        return postRepository.saveTipPost(targetUser, targetCertificate, postContent, recommendTags);
    }

    public void appendComment(TargetUser targetUser, TargetPost targetPost, CommentContent content) {
        postRepository.saveComment(targetUser, targetPost, content);
    }
}
