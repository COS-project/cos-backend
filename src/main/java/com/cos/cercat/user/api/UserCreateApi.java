package com.cos.cercat.user.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.user.app.UserCreateService;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.request.UserCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "유저 정보 생성 API")
public class UserCreateApi {

    private final UserCreateService userCreateService;

    @PostMapping("/users")
    @Operation(summary = "회원 정보 추가 생성")
    public Response<Void> createUserInfo(@RequestPart UserCreateRequest request,
                                         @RequestPart MultipartFile file,
                                         @AuthenticationPrincipal UserDTO user) {
        userCreateService.createUserInfo(request, file, user.getId());
        return Response.success("회원 정보 추가 생성 성공");
    }
}
