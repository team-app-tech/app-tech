package server.apptech.advertisementlike.domain;

import jakarta.persistence.*;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
public class AdvertisementLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advertisement_like_id ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}

