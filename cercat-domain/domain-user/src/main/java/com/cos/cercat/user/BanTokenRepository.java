package com.cos.cercat.user;


public interface BanTokenRepository {

    void ban(String banToken);

    boolean isLoginUser(String token);

}
