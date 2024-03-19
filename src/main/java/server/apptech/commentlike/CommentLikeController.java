package server.apptech.commentlike;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import server.apptech.auth.Auth;
import server.apptech.auth.AuthUser;

@RestController
@RequiredArgsConstructor
@Tag(name = "comment-like")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/like")
    @Operation(summary = "좋아요 추가", description = "댓글에 좋아요를 추가합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 좋아요 추가"),
            @ApiResponse(responseCode = "400", description = "이미 좋아요를 누른 댓글입니다.")}
    )
    public ResponseEntity<Long> addCommentLike(@Auth AuthUser authUser, @PathVariable(value = "commentId", required = true) Long commentId){
        return ResponseEntity.ok(commentLikeService.addCommentLike(authUser.getUserId(), commentId));
    }

    @DeleteMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/like")
    @Operation(summary = "좋아요 취소", description = "댓글에 좋아요를 취소합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 좋아요 취소"),
            @ApiResponse(responseCode = "400", description = "좋아요를 누르지 않은 댓글입니다.")}
    )
    public ResponseEntity<Void> cancelCommentLike(@Auth AuthUser authUser, @PathVariable(value = "commentId", required = true) Long commentId){
        commentLikeService.cancelCommentLike(authUser.getUserId(), commentId);
        return ResponseEntity.noContent().build();
    }
}
