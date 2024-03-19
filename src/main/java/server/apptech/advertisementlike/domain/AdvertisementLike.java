package server.apptech.advertisementlike.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AdvertisementLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static AdvertisementLike of(Advertisement advertisement, User user){
        return AdvertisementLike.builder()
                .advertisement(advertisement)
                .user(user)
                .build();
    }

}

