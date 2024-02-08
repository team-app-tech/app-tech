package server.apptech.user.domain;

import jakarta.persistence.*;
import server.apptech.global.domain.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    private Long authId;

    private String email;

    private String name;

    private String nickName;

    @Enumerated(value = EnumType.STRING)
    private UserAuthority role;

}
