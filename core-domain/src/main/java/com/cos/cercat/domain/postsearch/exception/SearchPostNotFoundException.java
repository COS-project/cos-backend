package com.cos.cercat.domain.postsearch.exception;

import com.cos.cercat.exception.DomainException;

public class SearchPostNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new SearchPostNotFoundException();

    private SearchPostNotFoundException() {
        super(PostSearchErrorCode.POST_SEARCH_ERROR_CODE);
    }
}
