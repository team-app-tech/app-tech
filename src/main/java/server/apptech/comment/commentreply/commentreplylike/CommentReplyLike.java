package server.apptech.comment.commentreply.commentreplylike;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.comment.commentlike.domain.CommentLike;
import server.apptech.comment.commentreply.domain.CommentReply;
import server.apptech.comment.domain.Comment;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentReplyLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_reply_id")
    private CommentReply commentReply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static CommentReplyLike of(CommentReply commentReply, User user) {
        return CommentReplyLike.builder()
                .commentReply(commentReply)
                .user(user)
                .build();
    }
}
