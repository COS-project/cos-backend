package com.cos.cercat.post;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
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
