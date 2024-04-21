package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionContent;
import com.cos.cercat.domain.mockexam.QuestionReader;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostAppender postAppender;
    private final QuestionReader questionReader;

    public void createCommentaryPost(TargetUser targetUser,
                                     TargetCertificate targetCertificate,
                                     PostContent postContent,
                                     MockExamSession mockExamSession,
                                     int questionSequence) {
        Question question = questionReader.read(targetCertificate, mockExamSession, questionSequence);
        postAppender.append(targetUser, targetCertificate, postContent, question);
    }

    public void createNormalPost(TargetUser targetUser,
                                 TargetCertificate targetCertificate,
                                 PostContent postContent) {


    }

    public void createTipPost(TargetUser targetUser,
                              TargetCertificate targetCertificate,
                              PostContent postContent) {


    }

}
