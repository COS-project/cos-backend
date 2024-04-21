package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadPostService {

    private final PostReader postReader;

    @Transactional(readOnly = true)
    public SliceResult<Post> searchCommentaryPost(TargetCertificate targetCertificate,
                                                  CommentaryPostSearchCond commentaryPostSearchCond,
                                                  Cursor cursor) {
        return postReader.read(targetCertificate, commentaryPostSearchCond, cursor);
    }

    public PostWithComments readDetail(TargetPost targetPost) {
        return postReader.readDetail(targetPost);
    }

}
