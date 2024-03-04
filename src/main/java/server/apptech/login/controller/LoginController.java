package server.apptech.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.apptech.login.dto.AccessTokenResponse;
import server.apptech.login.service.LoginService;
import server.apptech.login.dto.LoginRequest;
import server.apptech.login.domain.UserToken;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login")
@Slf4j
public class LoginController {

    public static final int COOKIE_AGE_SECONDS = 604800;
    private final LoginService loginService;

    @PostMapping(value = "/api/login")
    @Operation(summary = "로그인", description = "accessToken으로  로그인 합니다.", responses = {})
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest){

        UserToken userToken = loginService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(userToken.getRefreshToken()).toString())
                .body(new AccessTokenResponse(userToken.getAccessToken(), userToken.getAccessExpirationTime()));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from("refresh-token", refreshToken)
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
