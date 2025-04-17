package com.cos.cercat.domain.common;

public interface CountSynchronizer {

    long SCHEDULED_FIXED_RATE = 3000L; // 10 seconds

    void synchronize();

}
