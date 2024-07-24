package com.cos.cercat.apis.certificate.api;


import com.cos.cercat.apis.certificate.request.CertificateCreateRequest;
import com.cos.cercat.apis.certificate.request.CertificateExamCreateRequest;
import com.cos.cercat.apis.certificate.request.InterestCertificateCreateRequest;
import com.cos.cercat.domain.certificate.CreateCertificateService;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class CreateCertificateApi implements CreateCertificateApiDocs {

    private final CreateCertificateService createCertificateService;

    @PostMapping("/certificates")
    public Response<Void> certificateAdd(@RequestBody CertificateCreateRequest request) {
        createCertificateService.createCertificate(request.certificateName(), request.subjectInfoList());
        return Response.success("자격증 생성 성공");
    }

    @PostMapping("/certificates/{certificateId}/certificate-exams")
    public Response<Void> certificateExamAdd(@PathVariable Long certificateId,
                                            @RequestBody CertificateExamCreateRequest request) {
        createCertificateService.createCertificateExam(TargetCertificate.from(certificateId), request.toNewExamInformation());
        return Response.success("자격증 시험 정보 생성 성공");
    }

    @PostMapping("/interest-certificates")
    public Response<Void> InterestCertificateAdd(@RequestBody InterestCertificateCreateRequest request,
                                                     @AuthenticationPrincipal User currentUser) {
        createCertificateService.addInterestCertificates(TargetUser.from(currentUser.getId()), request.toInterestTargets());
        return Response.success("관심 자격증 리스트 생성 성공");
    }
}
