package com.cos.cercat.apis.user.app.usecase;

import com.cos.cercat.apis.user.dto.request.UserCreateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.infra.client.gcs.FileUploader;
import com.cos.cercat.entity.Image;
import com.cos.cercat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class UserUpdateUseCase {

    private final UserService userService;
    private final FileUploader fileUploader;

    public void updateUserInfo(UserCreateRequest request,
                               MultipartFile file,
                               Long userId) {
        List<Image> images = fileUploader.uploadFileInStorage(List.of(file));
        Image userImage = images.isEmpty() ? null : images.get(0);

        userService.updateUser(userId, request.nickname(), userImage);
    }

    @Transactional
    public void logout(String accessToken, String email) {
        userService.logout(accessToken, email);
        log.info("userEntity - {} 로그아웃", email);
    }

}
