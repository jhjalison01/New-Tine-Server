package com.umc.NewTine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.umc.NewTine.config.Role;
import com.umc.NewTine.dto.request.UserUpdateRequestDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Table(name="user")
public class User extends BaseTimeEntity implements UserDetails {
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
    public User(String nickname, String email, String image, Role role, String password, String provider, String providerId, int point) {
        this.nickname = nickname;
        this.email = email;
        this.image = image;
        this.role = Role.USER;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
        this.point = 0;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.getValue()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
