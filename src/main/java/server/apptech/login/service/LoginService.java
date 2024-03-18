package server.apptech.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.login.domain.*;
import server.apptech.login.dto.AccessTokenResponse;
import server.apptech.login.infrastructrue.JwtProvider;
import server.apptech.login.domain.repository.RefreshTokenRepository;
import server.apptech.login.dto.LoginRequest;
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

    public LoginUser login(LoginRequest loginRequest) {

        OauthProvider provider = oauthProviders.mapping(loginRequest.getSocialType());
        OauthUserInfo oauthUserInfo = provider.getUserInfo(loginRequest);


        User user = findOrCreateUser(oauthUserInfo);
        UserToken userToken = jwtProvider.generateLoginToken(user.getId().toString());

        RefreshToken savedRefreshToken = new RefreshToken(userToken.getRefreshToken(), user.getId());
        refreshTokenRepository.save(savedRefreshToken);

        return LoginUser.of(user, userToken);
    }

    public AccessTokenResponse renewalAccessToken(String refreshTokenRequest){

        jwtProvider.validRefreshToken(refreshTokenRequest);
        Long userId = refreshTokenRepository.findById(refreshTokenRequest).orElseThrow(()-> new AuthException(ExceptionCode.INVALID_REFRESH_TOKEN));
        return jwtProvider.regenerateAccessToken(userId.toString());
    }

    public void removeRefreshToken(String refreshToken){
        refreshTokenRepository.deleteById(refreshToken);
    }

    private User findOrCreateUser(OauthUserInfo oauthUserInfo) {

        return userService.findByAuthId(oauthUserInfo.getAuthId()).orElseGet(() -> createUser(oauthUserInfo));
    }

    private User createUser(OauthUserInfo oauthUserInfo){
        return userService.createUser(oauthUserInfo);
    }
}
