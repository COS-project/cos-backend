package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCertificateEntity is a Querydsl query type for CertificateEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertificateEntity extends EntityPathBase<CertificateEntity> {

    private static final long serialVersionUID = -2094251356L;

    public static final QCertificateEntity certificateEntity = new QCertificateEntity("certificateEntity");

    public final StringPath certificateName = createString("certificateName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCertificateEntity(String variable) {
        super(CertificateEntity.class, forVariable(variable));
    }

    public QCertificateEntity(Path<? extends CertificateEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCertificateEntity(PathMetadata metadata) {
        super(CertificateEntity.class, metadata);
    }

}

