package com.cos.cercat.cache.postsearch.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class SearchLogNotExistException extends InfraException {
    public static final InfraException EXCEPTION = new SearchLogNotExistException();

    private SearchLogNotExistException() {
        super(GlobalErrorCode.SEARCH_LOG_NOT_EXIST);
    }
}
