package com.cos.cercat.domain.common.event;

import java.util.UUID;

public interface Event {

    static String generateId() {
        return UUID.randomUUID().toString();
    }

    String resolveId();

    String resolveKey();

    String resolveType();

}
