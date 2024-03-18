package server.apptech.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class PageCommentResponse {
    private List<CommentResponse> commentResponses;
    public static PageCommentResponse of(List<CommentResponse> commentResponses) {
        return PageCommentResponse.builder()
                .commentResponses(commentResponses)
                .build();
    }
}
