package com.umc.NewTine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;
    private String nickname;
    private String picture;
    private String kakaoId;
    private String kakaoEmail;
    private String userId;
    private String password;
    //private enum press;
    //private enum interest;
    private LocalDateTime createdAt;
}
