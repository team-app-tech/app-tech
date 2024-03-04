package server.apptech.login.infrastructrue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import server.apptech.login.domain.OauthUserInfo;
import server.apptech.user.domain.SocialType;

public class KakaoUserInfo implements OauthUserInfo {

    @JsonProperty("id")
    private String authId;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @JsonIgnore
    private SocialType socialType;

    @Override
    public void setSocialType(SocialType socialType) {
        this.socialType = socialType;
    }

    @Override
    public String getAuthId() {
        return authId;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.kakaoProfile.nickname;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public String getImageUrl() {
        return kakaoAccount.kakaoProfile.imageUrl;
    }

    @Override
    public SocialType getSocialType() {
        return socialType;
    }

    @NoArgsConstructor
    private static class KakaoAccount{

        @JsonProperty("profile")
        private KakaoProfile kakaoProfile;

        @JsonProperty("email")
        private String email;
    }

    @NoArgsConstructor
    private static class KakaoProfile{

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("profile_image_url")
        private String imageUrl;
    }

}
