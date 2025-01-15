package com.cos.cercat.domain.certificate;


import com.cos.cercat.domain.user.User;

import java.util.List;

public interface InterestCertificateRepository {

    void saveAll(User user, List<NewInterestCertificate> newInterestCertificates);

    List<InterestCertificate> findByUser(User user);

    List<InterestCertificate> findByCertificate(Certificate certificate);

    void remove(User user, Certificate certificate);

    void removeAll(User user);

}
