package server.apptech.blame.domain;

import jakarta.persistence.*;
import server.apptech.comment.domain.Comment;
import server.apptech.global.domain.BaseEntity;

@Entity
public class Blame extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "comment_id")
    private Comment comment;
}
