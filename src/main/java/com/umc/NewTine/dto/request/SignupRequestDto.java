package com.umc.NewTine.dto.request;

import com.umc.NewTine.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String email;
    private String password;


    @Builder
    public SignupRequestDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {

        if(password.isEmpty())
            throw new IllegalArgumentException("비밀번호를 입력해주세요");
        else
            this.password = passwordEncoder.encode(password);
    }

    public User toEntity(SignupRequestDto signupRequestDto) {
        return User.builder()
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .build();
    }
}