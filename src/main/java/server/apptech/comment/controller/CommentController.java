package server.apptech.comment.controller;


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
import server.apptech.comment.dto.PageCommentResponse;
import server.apptech.comment.service.CommentService;
import server.apptech.comment.dto.CommentCreateRequest;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/api/advertisement/{advertisementId}/comment", consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = "댓글 생성", description = "댓글을 작성합니다.", responses = {@ApiResponse(responseCode = "200", description = "정상적으로 생성")})
    public ResponseEntity<?> createComment(@Auth AuthUser authUser, @PathVariable(value = "advertisementId", required = true) Long advertisementId, @RequestBody @Valid CommentCreateRequest commentCreateRequest){

        Long commentId = commentService.createComment(authUser.getUserId(),advertisementId, commentCreateRequest);
        return ResponseEntity.ok()
                .body(commentId);
    }

    @GetMapping(value = "/api/advertisement/{advertisementId}/comment")
    @Operation(summary = "댓글 조회", description = "댓글과 대댓글을 조회합니다.", responses = {@ApiResponse(responseCode = "200", description = "정상적으로 생성",content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageCommentResponse.class)))})
    public ResponseEntity<PageCommentResponse> getComments(@PathVariable(value = "advertisementId", required = true) Long advertisementId){
        return ResponseEntity.ok()
                .body(commentService.getCommentsByAdvertisementId(advertisementId));
    }
}
