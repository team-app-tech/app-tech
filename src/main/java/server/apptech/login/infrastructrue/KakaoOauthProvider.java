package server.apptech.login.infrastructrue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import server.apptech.login.dto.LoginRequest;
import server.apptech.login.domain.OauthProvider;
import server.apptech.login.domain.OauthUserInfo;
import server.apptech.user.domain.SocialType;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class KakaoOauthProvider implements OauthProvider {

    private static final SocialType socialType = SocialType.KAKAO;
    private static final String SECURE_RESOURCE = "secure_resource";
    private final String userUri;

    public KakaoOauthProvider(@Value("${oauth2.provider.kakao.user-info}")final String userUri){
        this.userUri = userUri;
    }

    @Override
    public boolean is(SocialType socialType) {
        if(this.socialType == socialType){
            return true;
        }
        return false;
    }

    @Override
    public OauthUserInfo getUserInfo(LoginRequest loginRequest) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginRequest.getOauthAccessToken());
        final HttpEntity<MultiValueMap<String, String>> userInfoRequestEntity = new HttpEntity<>(headers);

        final Map<String, Boolean> queryParam = new HashMap<>();
        queryParam.put(SECURE_RESOURCE, Boolean.TRUE);

        final ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                userUri,
                HttpMethod.GET,
                userInfoRequestEntity,
                KakaoUserInfo.class,
                queryParam
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            KakaoUserInfo kakaoUserInfo = response.getBody();
            kakaoUserInfo.setSocialType(socialType);
            return kakaoUserInfo;
        }
        throw new RuntimeException("허용되지 않은 접근");
    }
}
