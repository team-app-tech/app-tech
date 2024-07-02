package server.apptech.comment.commentreply.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.comment.commentreply.domain.CommentReply;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentReplyResponse {

    private Long commentReplyId;
    private String content;
    private String nickName;
    private Integer likeCnt;
    private String authId;
    private Boolean isLiked;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public static CommentReplyResponse of(CommentReply commentReply){
        return CommentReplyResponse.builder()
                .commentReplyId(commentReply.getId())
                .content(commentReply.getContent())
                .nickName(commentReply.getUser().getNickName())
                .likeCnt(commentReply.getLikeCnt())
                .authId(commentReply.getUser().getAuthId())
                .createdAt(commentReply.getCreatedAt())
                .build();
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }
}
