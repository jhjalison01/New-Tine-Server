package com.umc.NewTine.dto;

import com.umc.NewTine.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String userId;
    private String email;
    private String password;


    @Builder
    public SignupRequestDto(String username, String userId, String email, String password, String phone) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {

//        if(password.isEmpty())
//            throw new CustomException(ErrorCode.CANNOT_EMPTY_CONTENT);
//        else
        this.password = passwordEncoder.encode(password);
    }

    public User toEntity(SignupRequestDto signupRequestDto) {
        return User.builder()
                .userId(signupRequestDto.getUserId())
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .build();
    }
}