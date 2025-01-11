package com.cos.cercat.search.postsearch.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class ElasticSearchException extends InfraException {
    public static final InfraException EXCEPTION = new ElasticSearchException();

    private ElasticSearchException() {
        super(GlobalErrorCode.ES_SEARCH_ERROR);
    }
}
