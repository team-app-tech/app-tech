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

    Long commentId;
    String content;
    Long fileId;
    String fileUrl;
    String nickName;
    Integer likeCnt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private List<ChildComment> childComments;

    public static CommentResponse of(Comment comment){
        CommentResponseBuilder builder = CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getUser().getNickName())
                .likeCnt(comment.getCommentLikes().size())
                .createdAt(comment.getCreatedAt());
        if(comment.getFile() != null){
            builder.fileId(comment.getFile().getId());
            builder.fileUrl(comment.getFile().getUrl());
        }
        if(comment.getChildComments().size() > 0){
            builder.childComments(comment.getChildComments().stream().map(ChildComment::of).collect(Collectors.toList()));
        }
        return builder.build();
    }

}
