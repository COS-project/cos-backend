package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.response.CertificateDetailResponse;
import com.cos.cercat.apis.certificate.response.CertificateExamResponse;
import com.cos.cercat.apis.certificate.response.CertificateResponse;
import com.cos.cercat.apis.certificate.response.InterestCertificateResponse;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "자격증 조회 API")
public interface ReadCertificateApiDocs {

    @Operation(summary = "전체 자격증 조회")
    Response<List<CertificateResponse>> getCertificates();

    @GetMapping("/certificates/{certificateId}")
    Response<CertificateDetailResponse> getCertificateDetail(@PathVariable Long certificateId);

    @Operation(summary = "자격증 시험 정보 조회")
    Response<CertificateExamResponse> getCertificateExamDetail(Long certificateId);

    @Operation(summary = "나의 관심 자격증 조회")
    Response<List<InterestCertificateResponse>> getInterestCertificates(User currentUser);

}
