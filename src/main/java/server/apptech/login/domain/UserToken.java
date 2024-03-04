package server.apptech.login.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserToken {

    private String accessToken;
    private String refreshToken;

    private long accessExpirationTime;
    private long refreshExpirationTime;
}
