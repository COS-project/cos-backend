package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.TargetUser;

import java.util.List;

public interface PostRepository {

    void save(TargetUser targetUser,
              TargetCertificate targetCertificate,
              PostContent postContent,
              Question question);
}
