package com.cos.cercat.certificate.api;


import com.cos.cercat.certificate.app.CertificateCreateService;
import com.cos.cercat.certificate.dto.request.CertificateCreateRequest;
import com.cos.cercat.certificate.dto.request.CertificateExamCreateRequest;
import com.cos.cercat.certificate.dto.request.InterestCertificateCreateRequest;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "자격증 생성 API")
public class CertificateCreateApi {

    private final CertificateCreateService certificateCreateService;

    @PostMapping("/certificates")
    @Operation(summary = "자격증 생성")
    public Response<Void> createCertificate(@RequestBody CertificateCreateRequest request) {
        certificateCreateService.createCertificate(request);
        return Response.success("자격증 생성 성공");
    }

    @PostMapping("/certificates/{certificateId}/certificate-exams")
    @Operation(summary = "자격증 시험 정보 생성")
    public Response<Void> createCertificate(@PathVariable Long certificateId,
                                            @RequestBody CertificateExamCreateRequest request) {
        certificateCreateService.createCertificateExam(certificateId, request);
        return Response.success("자격증 시험 정보 생성 성공");
    }

    @PostMapping("/interest-certificates")
    @Operation(summary = "흥미 자격증 리스트 생성")
    public Response<Void> createInterestCertificates(@RequestBody List<InterestCertificateCreateRequest> requests,
                                                     @AuthenticationPrincipal UserDTO user) {
        certificateCreateService.createInterestCertificates(requests, user.getId());
        return Response.success("흥미 자격증 리스트 생성 성공");
    }
}
