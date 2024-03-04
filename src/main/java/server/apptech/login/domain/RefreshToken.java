package server.apptech.login.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshToken {

    private String refreshToken;
    private Long userId;
}
