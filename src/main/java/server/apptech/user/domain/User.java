package server.apptech.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.auth.Authority;
import server.apptech.global.domain.BaseEntity;
import server.apptech.login.domain.OauthUserInfo;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    @Column(name = "auth_id")
    private String authId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Authority role;
    private Integer point;

    public static User createTempuser(){
        User user = new User();
        return user;
    }

    public static User of(OauthUserInfo oauthUserInfo){
        return User.builder()
                .authId(oauthUserInfo.getAuthId())
                .socialType(oauthUserInfo.getSocialType())
                .email(oauthUserInfo.getEmail())
                .nickName(oauthUserInfo.getNickname() + generateRandomFourDigitCode())
                .profileImageUrl(oauthUserInfo.getImageUrl())
                .point(0)
                .build();
    }



    public void changeNickName(String nickName){
        this.nickName = nickName;
    }

    public void updateProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
    public void addPoint(int amount){
        this.point += amount;
    }

    private static String generateRandomFourDigitCode(){
        final int randomNumber = (int) (Math.random() * 10000);
        return "#" + String.format("%04d", randomNumber);
    }
}
