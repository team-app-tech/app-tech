package server.apptech.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.dto.CommentResponse;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyCommentResponse {
    private Long commentId;
    private String content;
    private String nickName;
    private Integer replyCnt;
    private Integer likeCnt;
    private String authId;
    private Boolean isLiked;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public static MyCommentResponse of(Comment comment){
        MyCommentResponseBuilder myCommentResponseBuilder = MyCommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getCommentUserNickName())
                .replyCnt(comment.getCommentRepliesCnt())
                .likeCnt(comment.getLikeCnt())
                .authId(comment.getUser().getAuthId())
                .createdAt(comment.getCreatedAt());
        return myCommentResponseBuilder.build();
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }
}
