package com.cos.cercat.domain.common;

public interface CountSynchronizer {

    Long SCHEDULED_FIXED_RATE = 10000L; // 10 seconds

    void synchronize();

}
