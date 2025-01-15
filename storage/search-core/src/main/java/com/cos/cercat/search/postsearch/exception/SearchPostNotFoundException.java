package com.cos.cercat.search.postsearch.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class SearchPostNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new SearchPostNotFoundException();

    private SearchPostNotFoundException() {
        super(GlobalErrorCode.SEARCH_POST_NOT_FOUND);
    }
}
