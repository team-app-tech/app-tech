package server.apptech.comment.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommentResponse {

    private Long commentId;
    private String content;
    private Long fileId;
    private String fileUrl;
    private String nickName;
    private Integer replyCnt;
    private Integer likeCnt;
    private String authId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public static CommentResponse of(Comment comment){
        CommentResponseBuilder builder = CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getUser().getNickName())
                .replyCnt(comment.getCommentRepliesCnt())
                .likeCnt(comment.getLikeCnt())
                .authId(comment.getUser().getAuthId())
                .createdAt(comment.getCreatedAt());
        if(comment.getFile() != null){
            builder.fileId(comment.getFile().getId());
            builder.fileUrl(comment.getFile().getUrl());
        }
        return builder.build();
    }

}
