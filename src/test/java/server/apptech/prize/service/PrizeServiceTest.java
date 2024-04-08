package server.apptech.prize.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.auth.Authority;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.file.domain.File;
import server.apptech.file.domain.FileType;
import server.apptech.prize.domain.repository.PrizeRepository;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class PrizeServiceTest {


    @InjectMocks PrizeService prizeService;
    @Mock
    PrizeRepository prizeRepository;
    @Mock
    AdvertisementRepository advertisementRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("상금 배분 로직 테스트")
    void getPrizePointTest() {

        // given
        long totalPrice = 50000;
        int totalLikeCnt = 123;
        int likeCnt = 54;
        // when
        int prizePoint = prizeService.getPrizePoint(totalPrice, totalLikeCnt, likeCnt);
        //then
        System.out.println(prizePoint);
        Assertions.assertThat(prizePoint).isEqualTo(21950);
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
                .build();
        return adCreateRequest;
    }

    private static User createUser() {
        User user = User.builder()
                .id(1L)
                .socialType(SocialType.KAKAO)
                .role(Authority.ROLE_USER)
                .name("user1")
                .email("test@emaill.com")
                .authId("1234")
                .nickName("user1")
                .build();
        return user;
    }
    private static User createUser2() {
        User user = User.builder()
                .id(2L)
                .socialType(SocialType.KAKAO)
                .role(Authority.ROLE_USER)
                .name("user2")
                .email("test@emaill.com")
                .authId("1234")
                .nickName("user2")
                .build();
        return user;
    }

    private static User createUser3() {
        User user = User.builder()
                .id(2L)
                .socialType(SocialType.KAKAO)
                .role(Authority.ROLE_USER)
                .name("user3")
                .email("test@emaill.com")
                .authId("1234")
                .nickName("user3")
                .build();
        return user;
    }
    private static Advertisement createAdvertisement(AdCreateRequest adCreateRequest, User user, File file, File file2) {
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
                .thumbNailImage(file)
                .contentImage(file2)
                .build();
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

    private static Comment createComment(User user, Advertisement advertisement) {
        return Comment.builder()
                .id(1L)
                .advertisement(advertisement)
                .commentLikes(new ArrayList<>())
                .user(user)
                .content("1번댓글")
                .likeCnt(10)
                .build();
    }
    private static Comment createComment2(User user, Advertisement advertisement) {
        return Comment.builder()
                .id(1L)
                .advertisement(advertisement)
                .commentLikes(new ArrayList<>())
                .user(user)
                .content("2번댓글")
                .likeCnt(8)
                .build();
    }private static Comment createComment3(User user, Advertisement advertisement) {
        return Comment.builder()
                .id(1L)
                .advertisement(advertisement)
                .commentLikes(new ArrayList<>())
                .user(user)
                .content("3번댓글")
                .likeCnt(6)
                .build();
    }
}