package server.apptech.login.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String accessToken;
    private String refreshToken;

    private long accessExpirationTime;
    private long refreshExpirationTime;

    private String nickName;
    private String email;
    private String profileImageUrl;

    public static LoginUser of(User user, UserToken userToken){
        return LoginUser.builder()
                .accessToken(userToken.getAccessToken())
                .refreshToken(userToken.getRefreshToken())
                .accessExpirationTime(userToken.getAccessExpirationTime())
                .refreshExpirationTime(userToken.getRefreshExpirationTime())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
