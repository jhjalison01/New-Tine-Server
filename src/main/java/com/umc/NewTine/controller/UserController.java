package com.umc.NewTine.controller;

import com.umc.NewTine.config.JwtTokenProvider;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.domain.UserPrincipal;
import com.umc.NewTine.dto.request.LoginRequestDto;
import com.umc.NewTine.dto.request.UserUpdateRequestDto;
import com.umc.NewTine.dto.response.LoginResponseDto;
import com.umc.NewTine.dto.request.SignupRequestDto;
import com.umc.NewTine.dto.response.UserDetailResponseDto;
import com.umc.NewTine.dto.response.UserResponseDto;
import com.umc.NewTine.dto.response.UserUpdateResponseDto;
import com.umc.NewTine.repository.UserRepository;
import com.umc.NewTine.service.MailService;
import com.umc.NewTine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final MailService mailService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto){
        if (userService.checkDuplicateUsers(signupRequestDto))
            throw new IllegalArgumentException("이미 사용된 메일입니다");
        signupRequestDto.encryptPassword(passwordEncoder);
        return userService.joinUser(signupRequestDto);
    }

    @PostMapping("/signin")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.findUserByEmail(loginRequestDto.getEmail().trim());

        if (!userRepository.existsByEmail(loginRequestDto.getEmail().trim())) {
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호를 찾을 수 없습니다.");
        }
//        System.out.println("email = " + loginRequestDto.getEmail()+ "password"+loginRequestDto.getPassword());
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        return jwtTokenProvider.generateToken(authentication);


        String access = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());
        String refresh = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole());

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .user(user)
                .accessToken(access)
                .refreshToken(refresh)
                .build();
        return loginResponseDto;
    }

//    @PostMapping("/refresh/token")
//    public RefreshTokenResponseDto refreshToken(@RequestHeader(value="KEY-EMAIL") String email,
//                                                @RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
//        RefreshTokenResponseDto refreshTokenResponseDto = tokenService.refreshToken(email, refreshToken);
//        return refreshTokenResponseDto;
//    }
    @PostMapping("login/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

        System.out.println("email = " + email);
        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

    @PatchMapping("/{userId}")
    public UserUpdateResponseDto updateUser(@PathVariable Long userId, @ModelAttribute  UserUpdateRequestDto updateRequestDto){
        return userService.updateUser(userId, updateRequestDto);
    }

    @GetMapping("/{userId}")
    public UserDetailResponseDto getUser(@AuthenticationPrincipal User user){
        System.out.println("user = " + user.getId());
        return userService.getUser(user.getId());
    }

}
