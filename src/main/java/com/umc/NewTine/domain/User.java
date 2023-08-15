package com.umc.NewTine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.umc.NewTine.config.Role;
import com.umc.NewTine.dto.request.UserUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@Table(name="user")
public class User extends BaseTimeEntity{
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String nickname;

    @Column
    private String email;

    @Column
    private String interest;


    @Column(columnDefinition = "TEXT")
    private String image;

    @Column
    private int point;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String provider; //어떤 OAuth인지

    @Column
    private String providerId;

    @OneToMany(mappedBy = "user")
    private List<UserNewsHistory> userNewsHistories;


    @Builder
    public User(String nickname, String email, String interest, String image, Role role, String password, String provider, String providerId) {
        this.nickname = nickname;
        this.email = email;
        this.interest = interest;
        this.image = image;
        this.role = role;
        this.point = 0;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void setPoint() {
        this.point = 0;
    }

    public void updatePoint(){
        this.point++;
    }

    public User update(String nickname, String image){
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public void updateUser(UserUpdateRequestDto userUpdateRequestDto){
        this.nickname = userUpdateRequestDto.getNickname();
        this.image = userUpdateRequestDto.getImage();
        this.interest = userUpdateRequestDto.getInterest();
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

}
