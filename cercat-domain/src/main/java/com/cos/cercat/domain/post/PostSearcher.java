package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSearcher {

    private final ReadPostRepository postRepository;

    public SliceResult<Post> search(TargetCertificate targetCertificate,
                                    CommentaryPostSearchCond commentaryPostSearchCond,
                                    Cursor cursor) {
        return postRepository.search(targetCertificate, commentaryPostSearchCond, cursor);
    }
}
