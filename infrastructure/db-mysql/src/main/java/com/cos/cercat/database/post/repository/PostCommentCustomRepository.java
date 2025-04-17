package com.cos.cercat.database.post.repository;

import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;
import org.springframework.data.domain.Pageable;

public interface PostCommentCustomRepository {

    SliceResult<Long> findDistinctPostIdsByUser(User user, Pageable pageable);

}
