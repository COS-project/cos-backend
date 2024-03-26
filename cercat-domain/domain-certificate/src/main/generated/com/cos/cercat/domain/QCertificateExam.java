package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCertificateExam is a Querydsl query type for CertificateExam
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertificateExam extends EntityPathBase<CertificateExam> {

    private static final long serialVersionUID = -963063008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCertificateExam certificateExam = new QCertificateExam("certificateExam");

    public final QCertificate certificate;

    public final QExamInfo examInfo;

    public final NumberPath<Integer> examYear = createNumber("examYear", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public QCertificateExam(String variable) {
        this(CertificateExam.class, forVariable(variable), INITS);
    }

    public QCertificateExam(Path<? extends CertificateExam> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCertificateExam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCertificateExam(PathMetadata metadata, PathInits inits) {
        this(CertificateExam.class, metadata, inits);
    }

    public QCertificateExam(Class<? extends CertificateExam> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificate = inits.isInitialized("certificate") ? new QCertificate(forProperty("certificate"), inits.get("certificate")) : null;
        this.examInfo = inits.isInitialized("examInfo") ? new QExamInfo(forProperty("examInfo"), inits.get("examInfo")) : null;
    }

}

