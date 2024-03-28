package server.apptech.comment.commentreply.commentreplylike;

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
@Tag(name = "Comment-Reply-Like")
public class CommentReplyLikeController {
    private final CommentReplyLikeService commentReplyLikeService;

    @PostMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/reply/{commentReplyId}/like")
    @Operation(summary = "좋아요 추가", description = "답글에 좋아요를 추가합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "정상적으로 좋아요 추가"),
            @ApiResponse(responseCode = "400", description = "이미 좋아요를 누른 답글입니다.")}
    )
    public ResponseEntity<Long> addCommentLike(@Auth AuthUser authUser, @PathVariable(value = "commentId", required = true) Long commentId, @PathVariable(value = "advertisementId", required = true) Long advertisementId, @PathVariable(value = "commentReplyId", required = true) Long commentReplyId){
        return ResponseEntity.ok(commentReplyLikeService.addCommentReplyLike(authUser.getUserId(), commentReplyId));
    }

    @DeleteMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/reply/{commentReplyId}/like")
    @Operation(summary = "좋아요 취소", description = "답글에 좋아요를 취소합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "정상적으로 좋아요 취소"),
            @ApiResponse(responseCode = "400", description = "좋아요를 누르지 않은 답글입니다.")}
    )
    public ResponseEntity<Void> cancelCommentLike(@Auth AuthUser authUser, @PathVariable(value = "commentId", required = true) Long commentId, @PathVariable(value = "advertisementId", required = true) Long advertisementId, @PathVariable(value = "commentReplyId", required = true) Long commentReplyId){
        commentReplyLikeService.cancelCommentReplyLike(authUser.getUserId(), commentReplyId);
        return ResponseEntity.noContent().build();
    }
}
