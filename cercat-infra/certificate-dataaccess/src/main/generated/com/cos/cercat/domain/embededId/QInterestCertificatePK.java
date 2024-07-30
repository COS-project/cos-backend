package com.cos.cercat.domain.embededId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterestCertificatePK is a Querydsl query type for InterestCertificatePK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QInterestCertificatePK extends BeanPath<InterestCertificatePK> {

    private static final long serialVersionUID = 1984745559L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterestCertificatePK interestCertificatePK = new QInterestCertificatePK("interestCertificatePK");

    public final com.cos.cercat.domain.QCertificateEntity certificateEntity;

    public final com.cos.cercat.domain.QUserEntity userEntity;

    public QInterestCertificatePK(String variable) {
        this(InterestCertificatePK.class, forVariable(variable), INITS);
    }

    public QInterestCertificatePK(Path<? extends InterestCertificatePK> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterestCertificatePK(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterestCertificatePK(PathMetadata metadata, PathInits inits) {
        this(InterestCertificatePK.class, metadata, inits);
    }

    public QInterestCertificatePK(Class<? extends InterestCertificatePK> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateEntity = inits.isInitialized("certificateEntity") ? new com.cos.cercat.domain.QCertificateEntity(forProperty("certificateEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new com.cos.cercat.domain.QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

