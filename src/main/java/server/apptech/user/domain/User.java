package server.apptech.user.domain;

import jakarta.persistence.*;
import server.apptech.global.domain.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserAuthority role;

}
