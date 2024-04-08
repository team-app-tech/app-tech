package server.apptech.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.apptech.auth.Auth;
import server.apptech.auth.AuthUser;
import server.apptech.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users / 사용자")
public class UserController {

    private final UserService userService;

    @PutMapping(value = "/api/users/nickname", consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = "닉네임 수정 ", description = "사용자 닉네임을 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 수정"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 닉네임")}
    )
    public ResponseEntity<?> updateAdvertisement(@Auth AuthUser authUser,
                                                 @RequestBody @Valid NickNameUpdateRequest nickNameUpdateRequest) {
        return ResponseEntity.ok()
                .body(userService.updateUserNickName(authUser.getUserId(), nickNameUpdateRequest));
    }
}
