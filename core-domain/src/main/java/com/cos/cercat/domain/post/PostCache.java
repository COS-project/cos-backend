package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import java.util.List;
import java.util.Optional;

public interface PostCache {

    void cacheTipPosts(Certificate certificate, List<Post> posts);

    Optional<List<Post>> getTipPosts(Certificate certificate);


}
