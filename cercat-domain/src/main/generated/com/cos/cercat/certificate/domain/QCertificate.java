package com.cos.cercat.certificate.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCertificate is a Querydsl query type for Certificate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertificate extends EntityPathBase<Certificate> {

    private static final long serialVersionUID = -1197546728L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCertificate certificate = new QCertificate("certificate");

    public final StringPath certificateName = createString("certificateName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSubjects subjects;

    public QCertificate(String variable) {
        this(Certificate.class, forVariable(variable), INITS);
    }

    public QCertificate(Path<? extends Certificate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCertificate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCertificate(PathMetadata metadata, PathInits inits) {
        this(Certificate.class, metadata, inits);
    }

    public QCertificate(Class<? extends Certificate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subjects = inits.isInitialized("subjects") ? new QSubjects(forProperty("subjects")) : null;
    }

}

