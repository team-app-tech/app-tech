package server.apptech.user;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PageMyCommentResponse {
    private List<MyCommentResponse> commentResponses;
    public static PageMyCommentResponse of(List<MyCommentResponse> commentResponses) {
        return PageMyCommentResponse.builder()
                .commentResponses(commentResponses)
                .build();
    }
}
