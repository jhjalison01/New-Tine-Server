package com.umc.NewTine.dto;

import com.umc.NewTine.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    String email;
    String password;

    @Builder
    public LoginRequestDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

}
