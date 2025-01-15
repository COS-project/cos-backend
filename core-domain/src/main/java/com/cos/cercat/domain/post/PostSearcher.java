package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSearcher {

    private final ReadPostRepository postRepository;

    public SliceResult<Post> search(Certificate certificate,
                                    CommentaryPostSearchCond commentaryPostSearchCond,
                                    Cursor cursor) {
        return postRepository.search(certificate, commentaryPostSearchCond, cursor);
    }
}
