package com.umc.NewTine.config;

import com.umc.NewTine.domain.UserPrincipal;
import com.umc.NewTine.dto.response.UserResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Component
@Slf4j
public class JwtTokenProvider {

    private final Key key;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    // 토큰 유효시간 3시간
    //private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 3 * 1000L;
    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 24 * 14 * 1000L; //일단 길게 설정
    // 14일
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 14 * 1000L;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //Authentication 을 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public UserResponseDto.TokenInfo generateToken(Authentication authentication) {
        return generateToken(authentication.getName(), authentication.getAuthorities());
    }

    //name, authorities 를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public UserResponseDto.TokenInfo generateToken(String name, Collection<? extends GrantedAuthority> inputAuthorities) {
        //권한 가져오기
        String authorities = inputAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        //Generate AccessToken
        String accessToken = Jwts.builder()
                .setSubject(name)
                .claim(AUTHORITIES_KEY, authorities)
                .claim("type", TYPE_ACCESS)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))  //토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        //Generate RefreshToken
        String refreshToken = Jwts.builder()
                .claim("type", TYPE_REFRESH)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME)) //토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return UserResponseDto.TokenInfo.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Access JWT 토큰 생성
    public String createAccessToken(String userPk, Role role) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put("roles", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // Refresh JWT 토큰 생성
    public String createRefreshToken(String userPk, Role role) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put("roles", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            //TODO:: Change Custom Exception
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        //클레임에서 권한 정보 가져오기
        List<GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //UserPrincipal 객체를 만들어서 Authentication 리턴
        UserPrincipal principal = new UserPrincipal(Long.valueOf(claims.getSubject()), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    //토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}