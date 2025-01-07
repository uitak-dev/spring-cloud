package com.example.user_service.security;

import com.example.user_service.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class UserContext implements UserDetails {

    private final UserDto userDto;
    private final List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userDto.getEncryptedPwd();
    }

    @Override
    public String getUsername() {
        return userDto.getEmail();
    }
}
