package server.apptech.point.domain;

import jakarta.persistence.*;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "balance")
    private Long balance;

}
