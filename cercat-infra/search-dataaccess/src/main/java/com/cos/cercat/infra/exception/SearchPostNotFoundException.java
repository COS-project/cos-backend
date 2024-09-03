package com.cos.cercat.infra.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class SearchPostNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new SearchPostNotFoundException();

    private SearchPostNotFoundException() {
        super(GlobalErrorCode.SEARCH_POST_NOT_FOUND);
    }
}
