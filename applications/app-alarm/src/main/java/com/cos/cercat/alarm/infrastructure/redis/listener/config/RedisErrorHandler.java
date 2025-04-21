package com.cos.cercat.alarm.infrastructure.redis.listener.config; // 적절한 패키지 위치에 생성

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler; // ErrorHandler 임포트

@Slf4j
@Component
public class RedisErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        Throwable current = t;
        while (current != null) {
            if (current instanceof RedisException && "Connection closed".equals(current.getMessage())) {
                return; // 오류 무시
            }
            current = current.getCause();
        }

        // Redis 연결 종료 오류가 아닌 다른 오류는 정상적으로 로깅
        log.error("Unexpected error occurred in Redis Stream listener task", t);
    }
}
