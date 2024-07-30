package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCertificateExamEntity is a Querydsl query type for CertificateExamEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertificateExamEntity extends EntityPathBase<CertificateExamEntity> {

    private static final long serialVersionUID = 1345899299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCertificateExamEntity certificateExamEntity = new QCertificateExamEntity("certificateExamEntity");

    public final QCertificateEntity certificateEntity;

    public final QExamInfoEntity examInfoEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCertificateExamEntity(String variable) {
        this(CertificateExamEntity.class, forVariable(variable), INITS);
    }

    public QCertificateExamEntity(Path<? extends CertificateExamEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCertificateExamEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCertificateExamEntity(PathMetadata metadata, PathInits inits) {
        this(CertificateExamEntity.class, metadata, inits);
    }

    public QCertificateExamEntity(Class<? extends CertificateExamEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateEntity = inits.isInitialized("certificateEntity") ? new QCertificateEntity(forProperty("certificateEntity")) : null;
        this.examInfoEntity = inits.isInitialized("examInfoEntity") ? new QExamInfoEntity(forProperty("examInfoEntity")) : null;
    }

}

