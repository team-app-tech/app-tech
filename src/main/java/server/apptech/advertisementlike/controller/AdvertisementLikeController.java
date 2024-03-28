package server.apptech.advertisementlike.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import server.apptech.advertisementlike.service.AdvertisementLikeService;
import server.apptech.auth.Auth;
import server.apptech.auth.AuthUser;


@RestController
@RequiredArgsConstructor
@Tag(name = "Advertisement-Like / 광고 좋아요")
public class AdvertisementLikeController {

    private final AdvertisementLikeService advertisementLikeService;

    @PostMapping(value = "/api/advertisement/{advertisementId}/like")
    @Operation(summary = "좋아요 추가", description = "광고에 좋아요를 추가합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 좋아요 추가"),
            @ApiResponse(responseCode = "400", description = "이미 좋아요를 누른 광고입니다.")}
    )
    public ResponseEntity<Long> addAdvertisementLike(@Auth AuthUser authUser, @PathVariable(value = "advertisementId", required = true) Long advertisementId){
        return ResponseEntity.ok(advertisementLikeService.addAdvertisementLike(authUser.getUserId(), advertisementId));
    }

    @DeleteMapping (value = "/api/advertisement/{advertisementId}/like")
    @Operation(summary = "좋아요 취소", description = "광고에 좋아요를 취소합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 좋아요 취소"),
            @ApiResponse(responseCode = "400", description = "좋아요를 누르지 않은 광고입니다.")}
    )
    public ResponseEntity<Void> cancelAdvertisementLike(@Auth AuthUser authUser, @PathVariable(value = "advertisementId", required = true) Long advertisementId){
        advertisementLikeService.cancelAdvertisementLike(authUser.getUserId(), advertisementId);
        return ResponseEntity.noContent().build();
    }
}
