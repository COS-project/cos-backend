package com.cos.cercat.certificate.api;

import com.cos.cercat.certificate.app.CertificateFetchService;
import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.certificate.dto.response.ExamInfoResponse;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    private final CertificateFetchService certificateFetchService;

    @GetMapping("/certificates")
    @Operation(summary = "전체 자격증 조회")
    public Response<List<CertificateResponse>> getCertificates() {
        return Response.success(certificateFetchService.getCertificates());
    }

    @GetMapping("/certificates/{certificateId}/exam-infos")
    @Operation(summary = "자격증 응시 정보 조회")
    public Response<ExamInfoResponse> getCertificateDetail(@PathVariable Long certificateId) {
        return Response.success(certificateFetchService.getCertificateWithInfo(certificateId));
    }

    @GetMapping("/certificates/{certificateId}/check-exam-date")
    @Operation(summary = "합격자 발표날이 지났는지 여부 조회")
    public Response<Boolean> isDatePassed(@PathVariable Long certificateId) {
        return Response.success(certificateFetchService.isExamDatePassed(certificateId));
    }
}
