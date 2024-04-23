package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.dto.response.CertificateExamResponse;
import com.cos.cercat.apis.certificate.dto.response.CertificateResponse;
import com.cos.cercat.apis.certificate.dto.response.InterestCertificateResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.ReadCertificateService;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
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
@RequestMapping("/api/v2")
@Tag(name = "자격증 조회 API")
public class ReadCertificateApi {

    private final ReadCertificateService readCertificateService;

    @GetMapping("/certificates")
    @Operation(summary = "전체 자격증 조회")
    public Response<List<CertificateResponse>> getCertificates() {
        List<Certificate> certificates = readCertificateService.readCertificates();
        return Response.success(certificates.stream()
                .map(CertificateResponse::from)
                .toList());
    }

    @GetMapping("/certificates/{certificateId}/certificate-exams")
    @Operation(summary = "자격증 시험 정보 조회")
    public Response<CertificateExamResponse> getCertificateExamDetail(@PathVariable Long certificateId) {
        CertificateExam recentCertificateExam = readCertificateService.readRecentCertificateExam(TargetCertificate.from(certificateId));
        return Response.success(CertificateExamResponse.from(recentCertificateExam));
    }

    @GetMapping("/interest-certificates")
    @Operation(summary = "나의 관심 자격증 조회")
    public Response<List<InterestCertificateResponse>> getInterestCertificates(@AuthenticationPrincipal UserDTO currentUser) {

        return Response.success(readCertificateService.readInterestCertificates(TargetUser.from(currentUser.getId())).stream()
                .map(InterestCertificateResponse::from)
                .toList());
    }
}
