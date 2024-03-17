package com.cos.cercat.post.app.search.service;

import com.cos.cercat.post.app.search.domain.PostDocument;
import com.cos.cercat.post.app.search.dto.PostCommentDebeziumDTO;
import com.cos.cercat.post.app.search.dto.PostDebeziumDTO;
import com.cos.cercat.post.app.search.dto.SearchCond;
import com.cos.cercat.post.app.search.repository.PostSearchRepository;
import com.cos.cercat.post.domain.PostType;
import com.cos.cercat.post.dto.request.PostSearchCond;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostSearchService {

    private final PostSearchRepository postSearchRepository;

    public void savePost(PostDebeziumDTO postDTO) {
        log.debug("savePost: {}", postDTO);
        postSearchRepository.save(postDTO.toEntity());
    }

    public void updatePost(PostDebeziumDTO postDTO) {
        log.debug("udpatePost: {}", postDTO);
        PostDocument post = findById(postDTO.getId());
        post.update(postDTO.toEntity());
        postSearchRepository.save(post);
    }

    public void delete(PostDebeziumDTO postDTO) {
        log.debug("delete: {}", postDTO);
        Assert.notNull(postDTO, "postDTO is null");
        postSearchRepository.delete(findById(postDTO.getId()));
    }

    public void saveComment(PostCommentDebeziumDTO commentDTO) {
        log.debug("saveComment: {}", commentDTO);
        PostDocument postDocument = findById(commentDTO.getPostId());
        postDocument.addComment(commentDTO.toEntity());
        postSearchRepository.save(postDocument);
    }

    public void deleteComment(PostCommentDebeziumDTO commentDTO) {
        log.debug("deleteComment: {}", commentDTO);
        Assert.notNull(commentDTO, "commentDTO is null");
        PostDocument postDocument = findById(commentDTO.getPostId());
        postDocument.removeComment(commentDTO.toEntity());
        postSearchRepository.save(postDocument);
    }

    public Slice<PostDocument> search(SearchCond cond, Long certificateId, Pageable pageable) {

        return postSearchRepository.search(cond, certificateId, pageable);
    }

    private PostDocument findById(Long id) {

        return postSearchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id %s was not found", id)));
    }


}
