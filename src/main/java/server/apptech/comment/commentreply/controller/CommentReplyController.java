package server.apptech.comment.commentreply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.apptech.auth.Auth;
import server.apptech.auth.AuthUser;
import server.apptech.comment.commentreply.dto.CommentReplyCreateRequest;
import server.apptech.comment.commentreply.dto.CommentReplyUpdateRequest;
import server.apptech.comment.commentreply.dto.PageCommentReplyResponse;
import server.apptech.comment.commentreply.service.CommentReplyService;
import server.apptech.comment.dto.PageCommentResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comment-Reply / 답글")
public class CommentReplyController {

    private final CommentReplyService commentReplyService;
    @PostMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/reply",produces ={MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = "답글 생성", description = "답글을 작성합니다.", responses = {@ApiResponse(responseCode = "200", description = "정상적으로 생성")})
    public ResponseEntity<?> createCommentReply(@Auth AuthUser authUser, @PathVariable(value = "advertisementId", required = true) Long advertisementId, @PathVariable(value = "commentId", required = true) Long commentId, @RequestBody @Valid CommentReplyCreateRequest commentReplyCreateRequest){

        Long commentReplyId = commentReplyService.createCommentReply(authUser.getUserId(),commentId, commentReplyCreateRequest);
        return ResponseEntity.ok()
                .body(commentReplyId);
    }

    @GetMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/reply")
    @Operation(summary = "답글 조회", description = "댓글에 해당하는 답글을 조회합니다.", responses = {@ApiResponse(responseCode = "200", description = "정상적으로 조회",content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageCommentResponse.class)))})
    public ResponseEntity<PageCommentReplyResponse> getCommentReplies(@Auth AuthUser user, @PathVariable(value = "advertisementId", required = true) Long advertisementId, @PathVariable(value = "commentId") Long commentId ){
        return ResponseEntity.ok()
                .body(commentReplyService.getCommentRepliesByCommentId(user, commentId));
    }

    @PutMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/reply/{commentReplyId}",produces ={MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = "답글 수정", description = "답글을 수정합니다.", responses = {@ApiResponse(responseCode = "200", description = "정상적으로 생성")})
    public ResponseEntity<?> updateCommentReply(@Auth AuthUser authUser, @PathVariable(value = "advertisementId", required = true) Long advertisementId,@PathVariable(value = "commentId", required = true) Long commentId, @PathVariable(value = "commentReplyId", required = true) Long commentReplyId, @RequestBody @Valid CommentReplyUpdateRequest commentReplyUpdateRequest){

        commentReplyService.updateCommentReply(authUser.getUserId(),commentReplyId,commentReplyUpdateRequest);
        return ResponseEntity.ok()
                .body(commentReplyId);
    }
    @DeleteMapping(value = "/api/advertisement/{advertisementId}/comment/{commentId}/reply/{commentReplyId}")
    @Operation(summary = "답글 삭제", description = "답글을 삭제합니다.", responses = {@ApiResponse(responseCode = "204", description = "댓글 삭제")})
    public ResponseEntity<?> deleteCommentReply(@Auth AuthUser authUser, @PathVariable(value = "advertisementId", required = true) Long advertisementId, @PathVariable(value = "commentId", required = true) Long commentId, @PathVariable(value = "commentReplyId", required = true) Long commentReplyId){

        commentReplyService.deleteCommentReply(authUser.getUserId(),commentReplyId);
        return ResponseEntity.noContent()
                .build();
    }
}
