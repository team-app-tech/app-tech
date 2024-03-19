package server.apptech.commentlike.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.comment.domain.Comment;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static CommentLike of(Comment comment, User user) {
        return CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();
    }
}
