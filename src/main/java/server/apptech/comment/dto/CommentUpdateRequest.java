package server.apptech.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    private String content;
    private Long fileId;
}
