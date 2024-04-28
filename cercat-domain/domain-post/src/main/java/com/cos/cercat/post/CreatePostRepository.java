package com.cos.cercat.post;


import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;

import java.util.Set;

public interface CreatePostRepository {

    TargetPost saveCommentaryPost(User user,
                                  Certificate certificate,
                                  PostContent postContent,
                                  Question question);

    TargetPost saveNormalPost(User user,
                              Certificate certificate,
                              PostContent postContent);

    TargetPost saveTipPost(User user,
                           Certificate certificate,
                           PostContent postContent,
                           Set<RecommendTag> recommendTags);

    void saveComment(User user,
                     Post post,
                     CommentContent content);
}
