package server.apptech.comment.comment;


import jakarta.persistence.*;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="advertisement_id")
    private Advertisement advertisement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Comment parent;

    @Column(name="content")
    private String content;
}
