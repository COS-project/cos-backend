package com.cos.cercat.domain.certificate.domain.embededId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInterestCertificatePK is a Querydsl query type for InterestCertificatePK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QInterestCertificatePK extends BeanPath<InterestCertificatePK> {

    private static final long serialVersionUID = 1930242384L;

    public static final QInterestCertificatePK interestCertificatePK = new QInterestCertificatePK("interestCertificatePK");

    public final NumberPath<Long> certificateId = createNumber("certificateId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QInterestCertificatePK(String variable) {
        super(InterestCertificatePK.class, forVariable(variable));
    }

    public QInterestCertificatePK(Path<? extends InterestCertificatePK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInterestCertificatePK(PathMetadata metadata) {
        super(InterestCertificatePK.class, metadata);
    }

}

