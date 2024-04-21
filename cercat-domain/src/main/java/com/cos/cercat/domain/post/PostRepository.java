package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.TargetUser;

import java.util.Set;

public interface PostRepository {

    TargetPost saveCommentaryPost(TargetUser targetUser,
                            TargetCertificate targetCertificate,
                            PostContent postContent,
                            Question question);

    TargetPost saveNormalPost(TargetUser targetUser,
                        TargetCertificate targetCertificate,
                        PostContent postContent);

    TargetPost saveTipPost(TargetUser targetUser,
                     TargetCertificate targetCertificate,
                     PostContent postContent,
                     Set<RecommendTag> recommendTags);
}
