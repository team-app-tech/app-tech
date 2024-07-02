package server.apptech.advertisement.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.apptech.advertisement.dto.AdUpdateRequest;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.dto.AdDetailResponse;
import server.apptech.file.FileRepository;
import server.apptech.file.domain.File;
import server.apptech.file.domain.FileType;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.global.scheduler.PrizeScheduler;
import server.apptech.payment.domain.Payment;
import server.apptech.payment.domain.repository.PaymentRepository;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;
import server.apptech.auth.Authority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    @InjectMocks
    AdvertisementService advertisementService;
    @Mock
    AdvertisementRepository advertisementRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    FileRepository fileRepository;
    @Mock
    PrizeScheduler prizeScheduler;
    @Mock
    PaymentRepository paymentRepository;
    @Test
    @DisplayName("광고가 정상 저장된후 아이디 반환(이미지 없음)")
    void saveAdvertisement() {
        //given
        AdCreateRequest adCreateRequest = createtAdCreateRequest();
        User user = createUser();
        File file = createFile();
        File file2 = createFile2();
        Payment payment = createPayment(user);
        Advertisement advertisement = createAdvertisement(adCreateRequest, user, file, file2, payment);

        given(advertisementRepository.save(any(Advertisement.class))).willReturn(advertisement);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(fileRepository.findById(file.getId())).willReturn(Optional.of(file));
        given(fileRepository.findById(file2.getId())).willReturn(Optional.of(file2));
        given(paymentRepository.findById(payment.getId())).willReturn(Optional.of(payment));
        willDoNothing().given(prizeScheduler).reservePrizeDistributionTask(advertisement.getId(), adCreateRequest.getEndDate());

        //when
        Long advertisementId = advertisementService.createAdvertisement(user.getId(), adCreateRequest);

        //then
        verify(advertisementRepository, times(1)).save(any(Advertisement.class));
        assertThat(advertisementId).isEqualTo(1L);
    }

    private static Payment createPayment(User user) {
        return Payment.builder()
                .id(1L)
                .paymentKey("paymentKey")
                .orderId("orderId")
                .orderName("orderName")
                .amount("10000")
                .user(user)
                .build();
    }

    @Test
    @DisplayName("광고글 목록 반환")
    void getAdvertisements(){
        
    }

    @Test
    @DisplayName("광고글 단건조회")
    void getAdvertisement(){

        //given
        AdCreateRequest adCreateRequest = createtAdCreateRequest();
        User user = createUser();
        File file = createFile();
        File file2 = createFile2();
        Payment payment = createPayment(user);
        Advertisement advertisement = createAdvertisement(adCreateRequest, user,file, file2,payment);
        given(advertisementRepository.findWithUserById(any(Long.class))).willReturn(Optional.of(advertisement));

        //when
//        AdDetailResponse adDetailResponse = advertisementService.getAdvertisementById(1L);

        //then
//        Assertions.assertThat(AdDetailResponse.of(advertisement)).usingRecursiveComparison()
//                .isEqualTo(adDetailResponse);
    }

    @Test
    @DisplayName("광고 정상적으로 수정")
    void updateAdvertisement(){
        //given
        AdCreateRequest adCreateRequest = createtAdCreateRequest();
        AdUpdateRequest adUpdateRequest = createUpdateRequest();
        User user = createUser();
        File file = createFile();
        File file2 = createFile2();
        Payment payment = createPayment(user);
        Advertisement advertisement = createAdvertisement(adCreateRequest, user,file, file2, payment);
        given(advertisementRepository.findWithUserById(any(Long.class))).willReturn(Optional.of(advertisement));
        given(advertisementRepository.save(any(Advertisement.class))).willReturn(advertisement);
        given(fileRepository.findById(file.getId())).willReturn(Optional.of(file));
        given(fileRepository.findById(file2.getId())).willReturn(Optional.of(file2));
        willDoNothing().given(prizeScheduler).modifyPrizeDistributionTask(advertisement.getId(), adUpdateRequest.getEndDate());

        //when
        Long updateAdvertisementId = advertisementService.updateAdvertisement(user.getId(), advertisement.getId(), adUpdateRequest);

        //then
        verify(advertisementRepository, times(1)).save(any(Advertisement.class));
        assertThat(updateAdvertisementId).isEqualTo(1L);
    }

    @Test
    @DisplayName("본인이 생성하지 않은 광고를 수정하려 할 때 예외 발생")
    void failUpdateAdvertisement(){
        //given
        AdCreateRequest adCreateRequest = createtAdCreateRequest();
        AdUpdateRequest adUpdateRequest = createUpdateRequest();
        User user = createUser();
        User user2 = createUser2();
        File file = createFile();
        File file2 = createFile2();
        Payment payment = createPayment(user);

        Advertisement advertisement = createAdvertisement(adCreateRequest, user,file, file2,payment);
        given(advertisementRepository.findWithUserById(any(Long.class))).willReturn(Optional.of(advertisement));

        //when && given
        Assertions.assertThatThrownBy(() -> advertisementService.updateAdvertisement(user2.getId(), advertisement.getId(), adUpdateRequest))
                .isInstanceOf(AuthException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.UNAUTHORIZED_USER_ACCESS);
    }

    @Test
    @DisplayName("이미 시작된 광고를 수정하려 할 때 예외 발생")
    void failUpdateAdvertisementByAlreadyStarted(){
        //given
        AdCreateRequest adCreateRequest = createAdCreateRequestAlreadyStarted();
        AdUpdateRequest adUpdateRequest = createUpdateRequest();
        User user = createUser();
        File file = createFile();
        File file2 = createFile2();
        Payment payment = createPayment(user);
        Advertisement advertisement = createAdvertisement(adCreateRequest, user,file, file2, payment);
        given(advertisementRepository.findWithUserById(any(Long.class))).willReturn(Optional.of(advertisement));

        //when && given
        Assertions.assertThatThrownBy(() -> advertisementService.updateAdvertisement(user.getId(), advertisement.getId(), adUpdateRequest))
                .isInstanceOf(RestApiException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.ALREADY_START_ADVERTISEMENT);
    }

    private AdCreateRequest createAdCreateRequestAlreadyStarted() {

        AdCreateRequest adCreateRequest = AdCreateRequest.builder()
                .title("제목")
                .content("내용")
                .totalPrice(10000L)
                .prizeWinnerCnt(10)
                .companyName("회사이름")
                .startDate(LocalDateTime.now().minusHours(1))
                .endDate(LocalDateTime.now().plusHours(2))
                .thumbNailImageId(1L)
                .contentImageId(2L)
                .build();
        return adCreateRequest;
    }


    private AdUpdateRequest createUpdateRequest() {

        AdUpdateRequest adUpdateRequest = AdUpdateRequest.builder()
                .title("수정")
                .content("수정내용")
                .prizeWinnerCnt(10)
                .companyName("회사이름")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .thumbNailImageId(1L)
                .contentImageId(2L)
                .build();
        return adUpdateRequest;

    }

    private static File createFile() {
        File file = File.builder()
                .id(1L)
                .fileType(FileType.IMAGE_JPEG)
                .uuid("uuid")
                .url("url")
                .build();
        return file;
    }

    private static File createFile2() {
        File file = File.builder()
                .id(2L)
                .fileType(FileType.IMAGE_JPEG)
                .uuid("uuid")
                .url("url")
                .build();
        return file;
    }

    private static Advertisement createAdvertisement(AdCreateRequest adCreateRequest, User user, File file, File file2, Payment payment) {
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
                .advertisementLikes(new ArrayList<>())
                .comments(new ArrayList<>())
                .payment(payment)
                .thumbNailImage(file)
                .contentImage(file2)
                .build();
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

    private static AdCreateRequest createtAdCreateRequest() {
        AdCreateRequest adCreateRequest = AdCreateRequest.builder()
                .title("제목")
                .content("내용")
                .totalPrice(10000L)
                .prizeWinnerCnt(10)
                .companyName("회사이름")
                .startDate(LocalDateTime.now().plusHours(1))
                .endDate(LocalDateTime.now().plusHours(2))
                .thumbNailImageId(1L)
                .contentImageId(2L)
                .paymentId(1L)
                .build();
        return adCreateRequest;
    }

}