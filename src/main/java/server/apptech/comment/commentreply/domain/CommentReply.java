package server.apptech.comment.commentreply.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.commentreply.dto.CommentReplyCreateRequest;
import server.apptech.comment.commentreply.dto.CommentReplyUpdateRequest;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentReply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="content")
    private String content;

    public static CommentReply of(User user, Comment comment, CommentReplyCreateRequest commentReplyCreateRequest){
        return CommentReply.builder()
                .user(user)
                .comment(comment)
                .content(commentReplyCreateRequest.getContent())
                .build();
    }

    public void updateComment(CommentReplyUpdateRequest commentReplyUpdateRequest) {
        this.content = commentReplyUpdateRequest.getContent();
    }
}
