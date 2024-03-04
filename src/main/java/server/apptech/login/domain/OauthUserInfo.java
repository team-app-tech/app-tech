package server.apptech.login.domain;

import server.apptech.user.domain.SocialType;

public interface OauthUserInfo {

    void setSocialType(SocialType socialType);

    String getAuthId();

    String getNickname();

    String getEmail();

    String getImageUrl();

    SocialType getSocialType();

}
