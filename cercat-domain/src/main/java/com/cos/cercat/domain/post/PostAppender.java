package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostAppender {

    private final PostRepository postRepository;

    public void append(TargetUser targetUser,
                       TargetCertificate targetCertificate,
                       PostContent postContent,
                       Question question) {

        postRepository.save(targetUser, targetCertificate, postContent, question);

    }
}
