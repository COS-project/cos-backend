//package com.cos.cercat.apis.user.app.usecase;
//
//import com.cos.cercat.apis.user.dto.request.UserCreateRequest;
//import com.cos.cercat.common.annotation.UseCase;
//import com.cos.cercat.entity.ImageEntity;
//import com.cos.cercat.gcs.GcsFileUploader;
//import com.cos.cercat.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Slf4j
//@UseCase
//@RequiredArgsConstructor
//public class UserUpdateUseCase {
//
//    private final UserService userService;
//    private final GcsFileUploader fileUploader;
//
//    public void updateUserInfo(UserCreateRequest request,
//                               MultipartFile file,
//                               Long userId) {
//        List<ImageEntity> imageEntities = fileUploader.uploadFileInStorage1(List.of(file));
//        ImageEntity userImageEntity = imageEntities.isEmpty() ? null : imageEntities.get(0);
//
//        userService.updateUser(userId, request.nickname(), userImageEntity);
//    }
//
//    @Transactional
//    public void logout(String accessToken, String email, long accessTokenExpirationMillis) {
//
//        userService.logout(accessToken, email, accessTokenExpirationMillis);
//        log.info("userEntity - {} 로그아웃", email);
//    }
//
//}
