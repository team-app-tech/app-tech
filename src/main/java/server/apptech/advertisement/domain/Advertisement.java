package server.apptech.advertisement.domain;

import jakarta.persistence.*;
import server.apptech.event.domain.Event;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
public class Advertisement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name="content")
    private String content;
    @Column(name = "view_cnt")
    private Integer viewCnt;

}
