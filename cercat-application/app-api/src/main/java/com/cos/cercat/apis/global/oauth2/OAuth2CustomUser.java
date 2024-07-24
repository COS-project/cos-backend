package com.cos.cercat.apis.global.oauth2;

import com.cos.cercat.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class OAuth2CustomUser implements OAuth2User, Serializable {


    private String registrationId;
    private Map<String, Object> attributes;
    @Getter
    private Role userRole;
    @Getter
    private String email;
    @Getter
    private Long userId;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return registrationId;
    }





}
