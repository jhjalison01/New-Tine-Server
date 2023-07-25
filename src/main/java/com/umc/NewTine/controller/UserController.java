package com.umc.NewTine.controller;

import com.umc.NewTine.config.JwtTokenProvider;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.LoginRequestDto;
import com.umc.NewTine.dto.LoginResponseDto;
import com.umc.NewTine.dto.SignupRequestDto;
import com.umc.NewTine.repository.UserRepository;
import com.umc.NewTine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto){
//        if (userService.checkDuplicateUsers(signupRequestDto))
//            throw new CustomException(ErrorCode.CANNOT_DUPLICATE_EMAIL);
        signupRequestDto.encryptPassword(passwordEncoder);
        return userService.joinUser(signupRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.findUserByEmail(loginRequestDto.getEmail().trim());

        if (!userRepository.existsByEmail(loginRequestDto.getEmail().trim())) {
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호를 찾을 수 없습니다.");
        }

        String access = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());
        String refresh = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole());

        tokenService.updateRefreshToken(loginRequestDto.getEmail(), refresh); //리프레시 토큰 저장

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .user(user)
                .accessToken(access)
                .refreshToken(refresh)
                .build();
        return loginResponseDto;
    }

    @PostMapping("/refresh/token")
    public RefreshTokenResponseDto refreshToken(@RequestHeader(value="KEY-EMAIL") String email,
                                                @RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        RefreshTokenResponseDto refreshTokenResponseDto = tokenService.refreshToken(email, refreshToken);
        return refreshTokenResponseDto;
    }

}
