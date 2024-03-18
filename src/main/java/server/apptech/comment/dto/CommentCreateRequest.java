package server.apptech.comment.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequest {

    private String content;
    private Long parentId;
    private Long fileId;
}
