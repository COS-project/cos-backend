package com.cos.cercat.domain.searchlog;


import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.post.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
    public SearchLog toSearchLog(Certificate certificate) {
        return new SearchLog(certificate.id(), keyword);
    }
}
