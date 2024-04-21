package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostAppender {

    private final PostRepository postRepository;

    public TargetPost appendCommentaryPost(TargetUser targetUser,
                                     TargetCertificate targetCertificate,
                                     PostContent postContent,
                                     Question question) {
        return postRepository.saveCommentaryPost(targetUser, targetCertificate, postContent, question);

    }

    public TargetPost appendNormalPost(TargetUser targetUser,
                                 TargetCertificate targetCertificate,
                                 PostContent postContent) {
        return postRepository.saveNormalPost(targetUser, targetCertificate, postContent);
    }

    public TargetPost appendTipPost(TargetUser targetUser,
                              TargetCertificate targetCertificate,
                              PostContent postContent,
                              Set<RecommendTag> recommendTags) {
        return postRepository.saveTipPost(targetUser, targetCertificate, postContent, recommendTags);
    }
}
