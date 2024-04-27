package com.cos.cercat.post;


import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.user.TargetUser;

import java.util.Set;

public interface CreatePostRepository {

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

    void saveComment(TargetUser targetUser, TargetPost targetPost, CommentContent content);
}
