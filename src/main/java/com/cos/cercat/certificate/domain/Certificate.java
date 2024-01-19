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

    private Long timeLimit;

    //TODO: 전체 문제 갯수 추가? 아니면 CertificateExam 엔티티를 새로 하나만들어서 연관관계설정?

    private Certificate(String certificate_name, Long timeLimit) {
        this.certificateName = certificate_name;
        this.timeLimit = timeLimit;
    }

}
