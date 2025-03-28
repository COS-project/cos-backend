package com.cos.cercat.domain.like;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeCounter {

    private final LikeCountBuffer likeCountBuffer;
    private final LikeCountRepository likeCountRepository;

    public void countUp(LikeTarget likeTarget) {
        likeCountBuffer.countUp(likeTarget);
    }

    public void countDown(LikeTarget likeTarget) {
        likeCountBuffer.countDown(likeTarget);
    }

    public List<LikeCount> getBufferedCounts() {
        return likeCountBuffer.getAll();
    }

    public LikeCount get(LikeTarget likeTarget) {
        return likeCountRepository.findByTarget(likeTarget)
                .orElseGet(() -> LikeCount.init(likeTarget));
    }

    public Map<Long, LikeCount> getConutMap(List<LikeTarget> likeTargets) {
        return likeCountRepository.findByTargets(likeTargets);
    }

}
