package server.apptech.comment.commentreply.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageCommentReplyResponse {

    private List<CommentReplyResponse> commentReplyResponses;
    public static PageCommentReplyResponse of(List<CommentReplyResponse> commentReplyResponses) {
        return PageCommentReplyResponse.builder()
                .commentReplyResponses(commentReplyResponses)
                .build();
    }
}
