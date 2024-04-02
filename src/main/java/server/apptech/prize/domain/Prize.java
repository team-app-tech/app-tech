package server.apptech.prize.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.comment.domain.Comment;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;


@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
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
    private Integer price;

    @Column(name = "ranking")
    private Integer ranking;

}
