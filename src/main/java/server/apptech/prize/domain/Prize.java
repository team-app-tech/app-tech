package server.apptech.prize.domain;

import jakarta.persistence.*;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.comment.comment.Comment;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
public class Prize extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id")
    private Comment comment;

    @Column(name = "price")
    private Long price;

    @Column(name = "ranking")
    private Integer ranking;

}
