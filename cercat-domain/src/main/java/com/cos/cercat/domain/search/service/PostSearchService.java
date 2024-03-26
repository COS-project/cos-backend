package com.cos.cercat.domain.search.service;

import com.cos.cercat.domain.search.domain.PostDocument;
import com.cos.cercat.domain.search.dto.PostCommentDebeziumDTO;
import com.cos.cercat.domain.search.dto.PostDebeziumDTO;
import com.cos.cercat.domain.search.dto.SearchCond;
import com.cos.cercat.domain.search.repository.PostSearchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostSearchService {

    private final PostSearchRepository postSearchRepository;

    public void savePost(PostDebeziumDTO postDTO) {
        log.debug("savePost: {}", postDTO);
        postSearchRepository.findById(postDTO.getId()).ifPresentOrElse(
                post -> {
                    post.update(postDTO.toEntity());
                    postSearchRepository.save(post);
                },
                () -> postSearchRepository.save(postDTO.toEntity()));
    }

    public void updatePost(PostDebeziumDTO postDTO) {
        log.debug("udpatePost: {}", postDTO);
        PostDocument post = findById(postDTO.getId());
        post.update(postDTO.toEntity());
        postSearchRepository.save(post);
    }

    public void delete(PostDebeziumDTO postDTO) {
        log.debug("delete: {}", postDTO);
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
        PostDocument postDocument = findById(commentDTO.getPostId());
        postDocument.removeComment(commentDTO.toEntity());
        postSearchRepository.save(postDocument);
    }

    public Slice<PostDocument> search(SearchCond cond, Long certificateId, Pageable pageable) {
        return postSearchRepository.search(cond, certificateId, pageable);
    }

    public List<String> getAutoCompletedKeywords(Long certificateId, String searchText) {
        return postSearchRepository.getAutoCompletedKeywords(certificateId, searchText);
    }

    public List<String> getRecentTop5Keywords(Long certificateId) {
        return postSearchRepository.getRecentTop5Keywords(certificateId);
    }

    private PostDocument findById(Long id) {

        return postSearchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id %s was not found", id)));
    }


}
