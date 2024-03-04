package server.apptech.login.domain;

import org.springframework.web.client.RestTemplate;
import server.apptech.login.dto.LoginRequest;
import server.apptech.user.domain.SocialType;

public interface OauthProvider {

    RestTemplate restTemplate = new RestTemplate();

    boolean is(SocialType socialType);
    OauthUserInfo getUserInfo(LoginRequest loginRequest);

}
