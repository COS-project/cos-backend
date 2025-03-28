package com.cos.cercat.redis.certificate;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateCache;
import com.cos.cercat.domain.certificate.CertificateId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateRedisCache implements CertificateCache {

    private final RedisTemplate<String, Certificate> redisTemplate;

    @Override
    public void cache(Certificate certificate) {
        String key = getKey(certificate.id());
        redisTemplate.opsForValue().setIfAbsent(key, certificate);
    }

    @Override
    public Optional<Certificate> get(CertificateId certificateId) {
        String key = getKey(certificateId);
        Certificate certificate = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(certificate);
    }

    @Override
    public void delete(CertificateId certificateId) {
        String key = getKey(certificateId);
        redisTemplate.delete(key);
    }

    private String getKey(CertificateId certificateId) {
        return "CERTIFICATE:" + certificateId.value();
    }
}
