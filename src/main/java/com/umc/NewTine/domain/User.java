package com.umc.NewTine.domain;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.umc.NewTine.config.Role;
import lombok.AccessLevel;
>>>>>>> origin/feature/#17-set-habit
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.umc.NewTine.config.Role;
import lombok.AccessLevel;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")

public class User extends BaseEntity{
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String nickname;

    @Column
    private String email;

    @Column(columnDefinition = "TEXT")
    private String image;

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

<<<<<<< HEAD
=======
    @Builder
    public User(String nickname, String email, String image, Role role, String password, String provider, String providerId) {
        this.nickname = nickname;
        this.email = email;
        this.image = image;
        this.role = role;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

>>>>>>> origin/feature/#17-set-habit

    public User update(String nickname, String image){
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

}
