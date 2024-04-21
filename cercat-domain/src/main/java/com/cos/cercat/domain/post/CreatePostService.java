package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionReader;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostAppender postAppender;
    private final QuestionReader questionReader;

    public TargetPost createCommentaryPost(TargetUser targetUser,
                                     TargetCertificate targetCertificate,
                                     PostContent postContent,
                                     MockExamSession mockExamSession,
                                     int questionSequence) {
        Question question = questionReader.read(targetCertificate, mockExamSession, questionSequence);
        return postAppender.appendCommentaryPost(targetUser, targetCertificate, postContent, question);
    }

    public TargetPost createNormalPost(TargetUser targetUser,
                                 TargetCertificate targetCertificate,
                                 PostContent postContent) {
        return postAppender.appendNormalPost(targetUser, targetCertificate, postContent);
    }

    public TargetPost createTipPost(TargetUser targetUser,
                              TargetCertificate targetCertificate,
                              PostContent postContent,
                              Set<RecommendTag> recommendTags) {

        return postAppender.appendTipPost(targetUser, targetCertificate, postContent, recommendTags);
    }

}
