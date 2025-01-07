package com.example.user_service.security;

import com.example.user_service.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // 어떤 요청에 대해서 해당 필터가 수행 되어야 할지 정의해야 한다.
    public RestAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        RequestLogin requestLogin = new ObjectMapper().readValue(messageBody, RequestLogin.class);

        // 요청에 ID와 PW가 없는 경우.
        if (!StringUtils.hasText(requestLogin.getEmail()) || !StringUtils.hasText(requestLogin.getPassword())) {
            throw new AuthenticationServiceException("Username or Password is not provided");
        }

        // 인증 받기 전에 ID와 PW로 인증 객체 생성.
        RestAuthenticationToken authenticationToken = new RestAuthenticationToken(requestLogin.getEmail(), requestLogin.getPassword());

        // 인증 처리는 AuthenticationManager 에게 위임, 인증 객체 반환.
        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
