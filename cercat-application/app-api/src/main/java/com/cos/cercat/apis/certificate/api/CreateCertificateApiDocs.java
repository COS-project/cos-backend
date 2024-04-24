package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.request.CertificateCreateRequest;
import com.cos.cercat.apis.certificate.request.CertificateExamCreateRequest;
import com.cos.cercat.apis.certificate.request.InterestCertificateCreateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "자격증 생성 API")
public interface CreateCertificateApiDocs {

    @Operation(summary = "자격증 생성")
    Response<Void> certificateAdd(CertificateCreateRequest request);

    @Operation(summary = "자격증 시험 정보 생성")
    Response<Void> certificateExamAdd(Long certificateId, CertificateExamCreateRequest request);

    @Operation(summary = "관심 자격증 리스트 생성")
    Response<Void> InterestCertificateAdd(InterestCertificateCreateRequest request, UserDTO currentUser);

}
