package com.cos.cercat.certificate.domain.entity;

import com.cos.cercat.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
