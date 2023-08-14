package com.umc.NewTine.config.auth;

import com.umc.NewTine.config.JwtTokenProvider;
import com.umc.NewTine.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다.");
            return;
        }

        OAuth2User principal = (OAuth2User)authentication.getPrincipal();

        //로그인 성공 시 jwt 토큰 발행
        log.info("userPrincipal attributes = {}", principal.getAttributes());
        logger.info("authentication.getName() = " + authentication.getName());
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);


        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login") //frontend page로 redirect
                .queryParam("token", tokenInfo.getAccessToken())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}