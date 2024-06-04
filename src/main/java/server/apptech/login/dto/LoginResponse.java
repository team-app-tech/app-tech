package server.apptech.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.login.domain.LoginUser;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;

    private long accessExpirationTime;

    private String nickName;
    private String email;
    private String profileImageUrl;
    private String authId;

    public static LoginResponse of(LoginUser loginUser){
        return LoginResponse.builder()
                .accessToken(loginUser.getAccessToken())
                .accessExpirationTime(loginUser.getAccessExpirationTime())
                .nickName(loginUser.getNickName())
                .email(loginUser.getEmail())
                .profileImageUrl(loginUser.getProfileImageUrl())
                .authId(loginUser.getAuthId())
                .build();
    }
}
