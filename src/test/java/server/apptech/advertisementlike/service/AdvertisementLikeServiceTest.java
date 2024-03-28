package server.apptech.advertisementlike.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.apptech.advertisement.advertisementlike.service.AdvertisementLikeService;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.advertisementlike.domain.AdvertisementLike;
import server.apptech.advertisement.advertisementlike.domain.repository.AdvertisementLikeRepository;
import server.apptech.auth.Authority;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdvertisementLikeServiceTest {

    @InjectMocks
    AdvertisementLikeService advertisementLikeService;
    @Mock
    AdvertisementLikeRepository advertisementLikeRepository;
    @Mock
    AdvertisementRepository advertisementRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("정상적으로 광고 좋아요 추가")
    void add_advertisement_like() {

        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(advertisementRepository.findById(any(Long.class))).willReturn(Optional.of(advertisement));
        given(advertisementLikeRepository.existsByAdvertisementIdAndUserId(advertisement.getId(), user.getId())).willReturn(Boolean.FALSE);
        given(advertisementLikeRepository.save(any(AdvertisementLike.class))).willReturn(advertisementLike);

        // when
        Long advertisementLikeId = advertisementLikeService.addAdvertisementLike(user.getId(), advertisement.getId());

        //then
        Assertions.assertThat(advertisementLikeId).isEqualTo(advertisementLike.getId());
        verify(advertisementLikeRepository, times(1)).save(any(AdvertisementLike.class));
    }

    @Test
    @DisplayName("이미 좋아요를 누른 경우 예외발생")
    void fail_add_advertisement_like_already_liked() {
        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(advertisementRepository.findById(any(Long.class))).willReturn(Optional.of(advertisement));
        given(advertisementLikeRepository.existsByAdvertisementIdAndUserId(advertisement.getId(), user.getId())).willReturn(Boolean.TRUE);

        // when && then
        Assertions.assertThatThrownBy(() -> advertisementLikeService.addAdvertisementLike(user.getId(), advertisement.getId()))
                .isInstanceOf(RestApiException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.ALREADY_LIKED_ADVERTISEMENT);
    }

    @Test
    @DisplayName("정상적으로 광고 좋아요 취소")
    void cancel_Advertisement_Like() {

        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(advertisementRepository.findById(any(Long.class))).willReturn(Optional.of(advertisement));
        given(advertisementLikeRepository.findByAdvertisementIdAndUserId(advertisement.getId(), user.getId())).willReturn(Optional.of(advertisementLike));

        // when
        advertisementLikeService.cancelAdvertisementLike(user.getId(), advertisement.getId());

        //then
        verify(advertisementLikeRepository, times(1)).delete(any(AdvertisementLike.class));
    }

    @Test
    @DisplayName("좋아요를 누르지 않았는데 취소하려고 할시 예외발생")
    void fail_cancel_Advertisement_Like_not_liked() {
        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        AdvertisementLike advertisementLike = AdvertisementLike.of(advertisement,user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(advertisementRepository.findById(any(Long.class))).willReturn(Optional.of(advertisement));
        given(advertisementLikeRepository.findByAdvertisementIdAndUserId(advertisement.getId(), user.getId())).willThrow(new RestApiException(ExceptionCode.ADVERTISEMENT_NOT_LIKED));

        // when && then
        Assertions.assertThatThrownBy(() -> advertisementLikeService.cancelAdvertisementLike(user.getId(), advertisement.getId()))
                        .isInstanceOf(RestApiException.class)
                        .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.ADVERTISEMENT_NOT_LIKED);
    }

    private static User createUser() {
        User user = User.builder()
                .id(1L)
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
                .id(2L)
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
                .id(1L)
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