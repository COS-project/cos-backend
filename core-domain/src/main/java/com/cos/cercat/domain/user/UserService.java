package com.cos.cercat.domain.user;

import com.cos.cercat.domain.common.File;
import com.cos.cercat.domain.common.FileUploader;
import com.cos.cercat.domain.common.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("api")
@RequiredArgsConstructor
public class UserService {

  private final UserReader userReader;
  private final UserRemover userRemover;
  private final UserUpdater userUpdater;
  private final FileUploader fileUploader;
  private final TokenManager tokenManager;


  public void updateUser(UserId userId,
      File file,
      String nickname) {
    Image image = fileUploader.upload(file);
    User user = userReader.read(userId);
    user.update(nickname, image);
    userUpdater.update(user);
  }

  public void logout(String accessToken) {
    tokenManager.banAccessToken(accessToken);
  }

  public void deleteUser(UserId userId) {
    User user = userReader.read(userId);
    userRemover.remove(user);
  }
}
