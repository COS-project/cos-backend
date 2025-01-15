package com.cos.cercat.apis.user.api;

import com.cos.cercat.apis.global.util.FileMapper;
import com.cos.cercat.apis.user.request.UserCreateRequest;
import com.cos.cercat.apis.user.response.UserResponse;
import com.cos.cercat.domain.common.File;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserService;
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
@RequestMapping("/api/v2")
@Tag(name = "유저 정보 삭제 API")
public class UserApi implements UserApiDocs {

    private final UserService userService;

    @GetMapping("/users/me")
    @Operation(summary = "회원 정보 가져오기")
    public Response<UserResponse> getMemberInfo(@AuthenticationPrincipal User currentUser) {
        return Response.success(UserResponse.from(currentUser));
    }

    @PatchMapping(path = "/users/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Void> updateUserInfo(@RequestPart UserCreateRequest request,
                                         @RequestPart(required = false) MultipartFile file,
                                         @AuthenticationPrincipal User currentUser) {
        userService.updateUser(
                TargetUser.from(currentUser.getId()),
                FileMapper.toFile(file),
                request.nickname()
        );
        return Response.success("회원 정보 추가 성공");
    }

    @PatchMapping("/logout")
    public Response<Void> logout(HttpServletRequest request) {
        userService.logout(request.getHeader("Access-Token"));
        return Response.success("로그아웃 성공");
    }

    @DeleteMapping("/users/me")
    @Operation(summary = "회원 탈퇴")
    public Response<Void> deleteUser(@AuthenticationPrincipal User currentUser) {
        userService.deleteUser(TargetUser.from(currentUser.getId()));
        return Response.success("회원탈퇴 성공");
    }

}
