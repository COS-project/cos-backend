package com.cos.cercat.es.postsearch.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class ElasticSearchException extends InfraException {
    public static final InfraException EXCEPTION = new ElasticSearchException();

    private ElasticSearchException() {
        super(GlobalErrorCode.ES_SEARCH_ERROR);
    }
}
