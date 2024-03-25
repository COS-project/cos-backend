package com.cos.cercat.certificate.api;

import com.cos.cercat.certificate.app.usecase.CertificateFetchUseCase;
import com.cos.cercat.certificate.dto.response.CertificateExamResponse;
import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.certificate.dto.response.InterestCertificateResponse;
import com.cos.cercat.dto.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "자격증 조회 API")
public class CertificateFetchApi {

    private final CertificateFetchUseCase certificateFetchUseCase;

    @GetMapping("/certificates")
    @Operation(summary = "전체 자격증 조회")
    public Response<List<CertificateResponse>> getCertificates() {
        return Response.success(certificateFetchUseCase.getCertificates());
    }

    @GetMapping("/certificates/{certificateId}/certificate-exams")
    @Operation(summary = "자격증 시험 정보 조회")
    public Response<CertificateExamResponse> getCertificateExamDetail(@PathVariable Long certificateId) {
        return Response.success(certificateFetchUseCase.getCertificateExam(certificateId));
    }

    @GetMapping("/interest-certificates")
    @Operation(summary = "나의 관심 자격증 조회")
    public Response<List<InterestCertificateResponse>> getInterestCertificates(@AuthenticationPrincipal UserDTO user) {
        return Response.success(certificateFetchUseCase.getInterestCertificate(user.getId()));
    }
}
