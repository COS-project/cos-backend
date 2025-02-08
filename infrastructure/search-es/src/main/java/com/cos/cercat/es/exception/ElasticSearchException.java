package com.cos.cercat.es.exception;

import com.cos.cercat.domain.postsearch.exception.PostSearchErrorCode;
import com.cos.cercat.exception.InfraException;

public class ElasticSearchException extends InfraException {
    public static final InfraException EXCEPTION = new ElasticSearchException();

    private ElasticSearchException() {
        super(ESErrorCode.ES_SEARCH_ERROR);
    }
}
