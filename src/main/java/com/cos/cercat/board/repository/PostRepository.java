package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from Image i where i.imageUrl in :imageUrls")
    void deleteAllByImageUrl(List<String> imageUrls);
}
