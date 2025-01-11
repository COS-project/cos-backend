package com.cos.cercat.cache.postsearch.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class SearchLogNotExistException extends InfraException {
    public static final InfraException EXCEPTION = new SearchLogNotExistException();

    private SearchLogNotExistException() {
        super(GlobalErrorCode.SEARCH_LOG_NOT_EXIST);
    }
}
