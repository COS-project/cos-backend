package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.response.CertificateExamResponse;
import com.cos.cercat.apis.certificate.response.CertificateResponse;
import com.cos.cercat.apis.certificate.response.InterestCertificateResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "자격증 조회 API")
public interface ReadCertificateApiDocs {

    @Operation(summary = "전체 자격증 조회")
    Response<List<CertificateResponse>> getCertificates();

    @Operation(summary = "자격증 시험 정보 조회")
    Response<CertificateExamResponse> getCertificateExamDetail(Long certificateId);

    @Operation(summary = "나의 관심 자격증 조회")
    Response<List<InterestCertificateResponse>> getInterestCertificates(UserDTO currentUser);

}
