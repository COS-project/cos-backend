package com.cos.cercat.apis.user.api;

import com.cos.cercat.apis.global.util.JwtTokenUtil;
import com.cos.cercat.apis.user.app.usecase.UserUpdateUseCase;
import com.cos.cercat.apis.user.dto.request.UserCreateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "유저 정보 추가 API")
public class UserUpdateApi {

    private final UserUpdateUseCase userUpdateUseCase;
    private final JwtTokenUtil jwtTokenUtil;

    @PatchMapping(path = "/users/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원 정보 추가")
    public Response<Void> updateUserInfo(@RequestPart UserCreateRequest request,
                                         @RequestPart MultipartFile file,
                                         @AuthenticationPrincipal UserDTO currentUser) {
        userUpdateUseCase.updateUserInfo(request, file, currentUser.getId());
        return Response.success("회원 정보 추가 성공");
    }

    @PatchMapping("/logout")
    @Operation(summary = "회원 로그아웃")
    public Response<Void> logout(HttpServletRequest request, @AuthenticationPrincipal UserDTO currentUser) {
        userUpdateUseCase.logout(
                request.getHeader("Access-Token"),
                currentUser.getEmail(),
                jwtTokenUtil.getAccessTokenExpirationMillis(request.getHeader("Access-Token"))
        );
        return Response.success("로그아웃 성공");
    }
}
