package server.apptech.commentreply.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.commentreply.domain.CommentReply;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentReplyResponse {

    Long commentReplyId;
    String content;
    String nickName;
    Integer likeCnt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public static CommentReplyResponse of(CommentReply commentReply){
        return CommentReplyResponse.builder()
                .commentReplyId(commentReply.getId())
                .content(commentReply.getContent())
                .nickName(commentReply.getUser().getNickName())
                .createdAt(commentReply.getCreatedAt())
                .build();
    }
}
