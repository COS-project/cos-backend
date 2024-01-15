package com.cos.cercat.certificate.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
public class Certificate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    private String certificateName;

    private Certificate(String certificate_name) {
        this.certificateName = certificate_name;
    }



    public static Certificate from(String certificateName) {
        return new Certificate(certificateName);
    }
}
