package com.cos.cercat.redis.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostCache;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRedisCache implements PostCache {

    private final RedisTemplate<String, List<Post>> postRedisTemplate;
    private final Duration TIP_POST_TTL = Duration.ofMinutes(5);

    @Override
    public void cacheTipPosts(Certificate certificate, List<Post> posts) {
        String key = getTipPostsKey(certificate);
        postRedisTemplate.opsForValue().setIfAbsent(key, posts, TIP_POST_TTL);
    }

    @Override
    public Optional<List<Post>> getTipPosts(Certificate certificate) {
        String key = getTipPostsKey(certificate);
        List<Post> posts = postRedisTemplate.opsForValue().get(key);
        return Optional.ofNullable(posts);
    }

    private String getTipPostsKey(Certificate certificate) {
        return "TIP_POSTS:" + certificate.id().value();
    }
}
