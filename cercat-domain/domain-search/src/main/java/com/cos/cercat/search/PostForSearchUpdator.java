package com.cos.cercat.search;

import com.cos.cercat.post.TargetPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostForSearchUpdator {
    
    private final PostForSearchRepository postForSearchRepository;
    
    public void update(PostForSearch domain) {
        PostForSearch postForSearch = postForSearchRepository.find(TargetPost.from(domain.getId()));
        postForSearch.update(domain);
        postForSearchRepository.save(postForSearch);
    }
    
}
