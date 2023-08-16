package com.umc.NewTine.dto.request;

import com.umc.NewTine.config.Role;
import com.umc.NewTine.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String provider;
    private String nickname;
    private String email;
    private String image;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String provider, String nickname, String email, String image) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.provider = provider;
        this.nickname = nickname;
        this.email = email;
        this.image = image;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
//        if("naver".equals(registrationId)){
//            return ofNaver(registrationId, userNameAttributeName, attributes);
//        }
        return ofKakao(registrationId, userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofKakao(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        // email
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // nickname, profile_image
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .provider(registrationId)
                .nickname((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .image((String) kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .build();
    }

    public User toEntity() {
        User user = User.builder()
                .nickname(nickname)
                .image(image)
                .email(email)
                .role(Role.USER)
                .provider(provider)
                .providerId(String.valueOf(attributes.get(nameAttributeKey)))
                .build();

        return user;
    }

}
