package server.apptech.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.auth.Auth;
import server.apptech.auth.AuthUser;
import server.apptech.user.service.UserService;

import java.io.IOException;

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

    @PostMapping(value = "/api/users/profile-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    @Operation(summary = "프로필 사진 ", description = "사용자 닉네임을 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 수정")
            }
    )
    public ResponseEntity<Long> updateProfileImage(@Auth AuthUser authUser,
                                          @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok()
                .body(userService.updateProfileImage(authUser.getUserId(), multipartFile));
    }
}
