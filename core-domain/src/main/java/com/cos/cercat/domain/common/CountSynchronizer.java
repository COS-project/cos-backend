package com.cos.cercat.domain.common;

public interface CountSynchronizer {

    long SCHEDULED_FIXED_RATE = 1000L; // 10 seconds

    void synchronize();

}
