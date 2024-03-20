package server.apptech.advertisementlike.domain.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisementlike.domain.AdvertisementLike;
import server.apptech.auth.Authority;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AdvertisementLikeRepositoryTest {

    @Autowired
    private AdvertisementLikeRepository advertisementLikeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Test
    @DisplayName("광고 ID와 USERID에 해당하는 advertisementLike가 있음")
    void exists_by_advertisement_id_and_userId() {

        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        advertisementRepository.save(advertisement);
        userRepository.save(user);
        advertisementLikeRepository.save(advertisementLike);
        // when
        boolean flag = advertisementLikeRepository.existsByAdvertisementIdAndUserId(advertisement.getId(), user.getId());

        //then
        assertThat(flag).isTrue();
    }

    @Test
    @DisplayName("광고 ID와 USERID에 해당하는 advertisementLike가 있음, 사용자가 다름")
    void non_exists_by_advertisement_id_and_userId_1() {

        // given
        User user = createUser();
        User user2 = createUser2();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        advertisementRepository.save(advertisement);
        userRepository.save(user);
        advertisementLikeRepository.save(advertisementLike);
        // when
        boolean flag = advertisementLikeRepository.existsByAdvertisementIdAndUserId(advertisement.getId(), user2.getId());

        //then
        assertThat(flag).isFalse();
    }
    @Test
    @DisplayName("광고 ID와 USERID에 해당하는 advertisementLike가 있음, 광고가 다름")
    void non_exists_by_advertisement_id_and_userId_2() {

        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Advertisement advertisement2 = createAdvertisement2(createtAdCreateRequest(), user);

        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        advertisementRepository.save(advertisement);
        userRepository.save(user);
        advertisementLikeRepository.save(advertisementLike);
        // when
        boolean flag = advertisementLikeRepository.existsByAdvertisementIdAndUserId(advertisement2.getId(), user.getId());

        //then
        assertThat(flag).isFalse();
    }

    @Test
    @DisplayName("광고 ID와 USERID에 해당하는 advertisementLike 찾아서 가져옴")
    void find_by_advertisement_id_and_user_id() {

        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        advertisementRepository.save(advertisement);
        userRepository.save(user);
        advertisementLikeRepository.save(advertisementLike);
        // when
        AdvertisementLike adlike = advertisementLikeRepository.findByAdvertisementIdAndUserId(advertisement.getId(), user.getId()).get();
        //then
        assertThat(adlike.getId()).isEqualTo(adlike.getId());
    }

    @Test
    @DisplayName("광고 ID와 USERID에 일치하는 advertisementLike 없음")
    void not_find_by_advertisement_id_and_user_id() {

        // given
        User user = createUser();
        User user2 = createUser2();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        advertisementRepository.save(advertisement);
        userRepository.save(user);
        advertisementLikeRepository.save(advertisementLike);

        // when && then
        assertThatThrownBy(() ->advertisementLikeRepository.findByAdvertisementIdAndUserId(advertisement.getId(), user2.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    private static User createUser() {
        User user = User.builder()
                .socialType(SocialType.KAKAO)
                .role(Authority.ROLE_USER)
                .name("test")
                .email("test@emaill.com")
                .authId("1234")
                .nickName("nickName")
                .build();
        return user;
    }

    private static User createUser2() {
        User user = User.builder()
                .socialType(SocialType.KAKAO)
                .role(Authority.ROLE_USER)
                .name("test")
                .email("test@emaill.com")
                .authId("1234")
                .nickName("nickName")
                .build();
        return user;
    }

    private static Advertisement createAdvertisement(AdCreateRequest adCreateRequest, User user) {
        return Advertisement.builder()
                .user(user)
                .title(adCreateRequest.getTitle())
                .content(adCreateRequest.getContent())
                .viewCnt(0)
                .totalPrice(adCreateRequest.getTotalPrice())
                .prizeWinnerCnt(adCreateRequest.getPrizeWinnerCnt())
                .companyName(adCreateRequest.getCompanyName())
                .startDate(adCreateRequest.getStartDate())
                .endDate(adCreateRequest.getEndDate())
                .files(new ArrayList<>())
                .advertisementLikes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }
    private static Advertisement createAdvertisement2(AdCreateRequest adCreateRequest, User user) {
        return Advertisement.builder()
                .user(user)
                .title(adCreateRequest.getTitle())
                .content(adCreateRequest.getContent())
                .viewCnt(0)
                .totalPrice(adCreateRequest.getTotalPrice())
                .prizeWinnerCnt(adCreateRequest.getPrizeWinnerCnt())
                .companyName(adCreateRequest.getCompanyName())
                .startDate(adCreateRequest.getStartDate())
                .endDate(adCreateRequest.getEndDate())
                .files(new ArrayList<>())
                .advertisementLikes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    private static AdCreateRequest createtAdCreateRequest() {
        AdCreateRequest adCreateRequest = AdCreateRequest.builder()
                .title("제목")
                .content("내용")
                .totalPrice(10000L)
                .prizeWinnerCnt(10)
                .companyName("회사이름")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        return adCreateRequest;
    }
}