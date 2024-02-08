package server.apptech.commentlike.domain;

import jakarta.persistence.*;
import server.apptech.comment.comment.Comment;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
public class CommentLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
