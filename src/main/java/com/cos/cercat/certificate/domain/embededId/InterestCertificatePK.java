package com.cos.cercat.certificate.domain.embededId;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InterestCertificatePK implements Serializable {

    private Long certificateId;

    private Long userId;
}
