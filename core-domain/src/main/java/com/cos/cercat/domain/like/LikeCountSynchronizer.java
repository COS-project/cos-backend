package com.cos.cercat.domain.like;

import com.cos.cercat.domain.common.CountSynchronizer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeCountSynchronizer implements CountSynchronizer {

    private final LikeCounter likeCounter;
    private final LikeCountRepository likeCountRepository;

    @Override
    public void synchronize() {
        List<LikeCount> bufferedCounts = likeCounter.getBufferedCounts();
        likeCountRepository.update(bufferedCounts);
    }

}
