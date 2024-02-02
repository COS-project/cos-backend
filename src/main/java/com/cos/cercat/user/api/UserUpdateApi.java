package com.cos.cercat.user.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.user.app.UserCreateService;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.request.UserCreateRequest;
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

    private final UserCreateService userCreateService;
    private final UserService userService;

    @PatchMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원 정보 추가", description = "회원 정보를 수정할 때도 사용 가능")
    public Response<Void> createUserInfo(@RequestPart UserCreateRequest request,
                                         @RequestPart MultipartFile file,
                                         @AuthenticationPrincipal UserDTO user) {
        userCreateService.createUserInfo(request, file, user.getId());
        return Response.success("회원 정보 추가 성공");
    }

    @PatchMapping("/logout")
    @Operation(summary = "회원 로그아웃")
    public Response<Void> logout(HttpServletRequest request, @AuthenticationPrincipal UserDTO user) {
        userService.logout(request.getHeader("Access-Token"), user.getEmail());
        return Response.success("로그아웃 성공");
    }
}
