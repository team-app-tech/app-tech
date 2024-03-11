package server.apptech.comment.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.comment.domain.Comment;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChildComment{

    Long commentId;
    String content;
    String nickName;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public static ChildComment of(Comment comment){
        return ChildComment.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getUser().getNickName())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}