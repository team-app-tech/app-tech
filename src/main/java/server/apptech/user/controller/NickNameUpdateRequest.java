package server.apptech.user.controller;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NickNameUpdateRequest {
    private String nickName;
}
