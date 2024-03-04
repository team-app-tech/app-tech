package server.apptech.login.dto;

import lombok.Getter;
import server.apptech.user.domain.SocialType;

@Getter
public class LoginRequest {

    private SocialType socialType;
    private String oauthAccessToken;

}
