package server.apptech.login.domain;

import org.springframework.stereotype.Component;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.user.domain.SocialType;

import java.util.List;

@Component
public class OauthProviders {

    private final List<OauthProvider> providers;

    public OauthProviders(List<OauthProvider> providers){
        this.providers = providers;
    }

    public OauthProvider mapping(SocialType socialType){
        return providers.stream()
                .filter(provider -> provider.is(socialType))
                .findFirst()
                .orElseThrow(() -> new AuthException(ExceptionCode.INVALID_SOCIAL_TYPE));
    }


}
