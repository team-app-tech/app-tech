package server.apptech.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.login.infrastructrue.JwtProvider;
import server.apptech.login.domain.OauthProvider;
import server.apptech.login.domain.OauthProviders;
import server.apptech.login.domain.OauthUserInfo;
import server.apptech.login.domain.repository.RefreshTokenRepository;
import server.apptech.login.dto.LoginRequest;
import server.apptech.login.domain.RefreshToken;
import server.apptech.login.domain.UserToken;
import server.apptech.user.UserService;
import server.apptech.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final OauthProviders oauthProviders;
    private final JwtProvider jwtProvider;

    private final UserService userService;

    private final RefreshTokenRepository refreshTokenRepository;

    public UserToken login(LoginRequest loginRequest) {

        OauthProvider provider = oauthProviders.mapping(loginRequest.getSocialType());
        OauthUserInfo oauthUserInfo = provider.getUserInfo(loginRequest);


        User user = findOrCreateUser(oauthUserInfo);

        UserToken userToken = jwtProvider.generateLoginToken(user.getId().toString());
        RefreshToken savedRefreshToken = new RefreshToken(userToken.getRefreshToken(), user.getId());
        refreshTokenRepository.save(savedRefreshToken);
        return userToken;
    }

    private User findOrCreateUser(OauthUserInfo oauthUserInfo) {

        return userService.findByAuthId(oauthUserInfo.getAuthId()).orElseGet(() -> createUser(oauthUserInfo));
    }

    private User createUser(OauthUserInfo oauthUserInfo){
        return userService.createUser(oauthUserInfo);
    }
}
