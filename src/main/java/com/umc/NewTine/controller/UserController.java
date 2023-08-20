package com.umc.NewTine.controller;

import com.umc.NewTine.config.JwtTokenProvider;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.domain.UserPrincipal;
import com.umc.NewTine.dto.request.ImageRequestDto;
import com.umc.NewTine.dto.request.LoginRequestDto;
import com.umc.NewTine.dto.request.UserUpdateRequestDto;
import com.umc.NewTine.dto.response.*;
import com.umc.NewTine.dto.request.SignupRequestDto;
import com.umc.NewTine.repository.UserRepository;
import com.umc.NewTine.service.ImageService;
import com.umc.NewTine.service.MailService;
import com.umc.NewTine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;
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
    @GetMapping("login/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

        System.out.println("email = " + email);
        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

    @PatchMapping("/update")
    public UserUpdateResponseDto updateUser(@RequestBody UserUpdateRequestDto updateRequestDto, @AuthenticationPrincipal User user){
        return userService.updateUser(user.getId(), updateRequestDto);
    }

    @GetMapping("/info")
    public UserDetailResponseDto getUser(@AuthenticationPrincipal User user){
        System.out.println("user = " + user.getId());
        return userService.getUser(user.getId());
    }

    @PostMapping("/image")
    public Long upload(@ModelAttribute ImageRequestDto imageRequestDto, @AuthenticationPrincipal User user) {
        imageService.upload(imageRequestDto, user.getEmail());

        return user.getId();
    }

    @GetMapping("/interest")
    public BaseResponse<Void> updateUserInterest(@RequestParam("category") String category, @AuthenticationPrincipal User user){
        if (userService.updateUserInterest(category, user.getId())){
            return new BaseResponse<>(true, HttpStatus.OK.value(),"Success");
        } else{
            return new BaseResponse<>(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"Fail");
        }
    }

}
