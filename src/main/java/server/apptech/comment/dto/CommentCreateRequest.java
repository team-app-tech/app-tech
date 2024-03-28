package server.apptech.comment.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateRequest {

    private String content;
    private Long fileId;
}
