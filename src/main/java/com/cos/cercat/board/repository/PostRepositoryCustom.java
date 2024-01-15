package com.cos.cercat.board.repository;

import com.cos.cercat.certificate.domain.Certificate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, Certificate certificate, String keyword);
}
