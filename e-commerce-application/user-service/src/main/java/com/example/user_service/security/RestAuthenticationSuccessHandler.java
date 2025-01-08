package com.example.user_service.security;

import com.example.user_service.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component("authenticationSuccessHandler")
@RequiredArgsConstructor
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${token.expiration-time}")
    private String tokenExpirationTime;

    @Value("${token.secret}")
    private String tokenSecret;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();

        UserDto userDto = (UserDto) authentication.getPrincipal();
        userDto.setPwd(null);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenSecret));
        String token = Jwts.builder()
                .subject(userDto.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(tokenExpirationTime))
                )
                .signWith(secretKey)
                .compact();

        response.addHeader("token", token);

//        mapper.writeValue(response.getWriter(), userDto);
    }
}
