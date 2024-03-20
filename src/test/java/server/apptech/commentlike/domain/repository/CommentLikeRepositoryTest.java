package server.apptech.commentlike.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisementlike.domain.repository.AdvertisementLikeRepository;
import server.apptech.auth.Authority;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.commentlike.domain.CommentLike;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CommentLikeRepositoryTest {

    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Test
    @DisplayName("댓글 ID와 USER ID에 해당하는 commentLike가 있음")
    void existsByCommentIdAndUserId() {

        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentLike commentLike = CommentLike.of(comment, user);
        userRepository.save(user);
        advertisementRepository.save(advertisement);
        commentRepository.save(comment);
        commentLikeRepository.save(commentLike);
        // when
        boolean flag = commentLikeRepository.existsByCommentIdAndUserId(comment.getId(), user.getId());
        //then
        Assertions.assertThat(flag).isTrue();
    }
    @Test
    @DisplayName("댓글 ID와 USER ID에 해당하는 commentLike가 없음")
    void non_exists_by_comment_id_and_user_id() {

        // given
        User user = createUser();
        User user2 = createUser2();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentLike commentLike = CommentLike.of(comment, user);
        userRepository.save(user);
        advertisementRepository.save(advertisement);
        commentRepository.save(comment);
        commentLikeRepository.save(commentLike);
        // when
        boolean flag = commentLikeRepository.existsByCommentIdAndUserId(comment.getId(), user2.getId());
        //then
        Assertions.assertThat(flag).isFalse();
    }

    @Test
    @DisplayName("광고 ID와 USERID에 해당하는 advertisementLike 찾아서 반환")
    void find_by_comment_id_and_user_id() {
        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentLike commentLike = CommentLike.of(comment, user);
        userRepository.save(user);
        advertisementRepository.save(advertisement);
        commentRepository.save(comment);
        commentLikeRepository.save(commentLike);
        // when
        CommentLike cl = commentLikeRepository.findByCommentIdAndUserId(comment.getId(), user.getId()).get();
        //then
        Assertions.assertThat(cl.getId()).isEqualTo(commentLike.getId());
    }

    @Test
    @DisplayName("댓글 ID와 USERID에 해당하는 advertisementLike 가 존재하지 않음")
    void fail_find_by_comment_id_and_user_id() {
        // given
        User user = createUser();
        User user2 = createUser2();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentLike commentLike = CommentLike.of(comment, user);
        userRepository.save(user);
        advertisementRepository.save(advertisement);
        commentRepository.save(comment);
        commentLikeRepository.save(commentLike);
        // when && then
        assertThatThrownBy(() ->commentLikeRepository.findByCommentIdAndUserId(comment.getId(), user2.getId()).get())
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

    private static CommentCreateRequest createCommentRequest() {
        return CommentCreateRequest.builder()
                .content("테스트 content")
                .build();
    }

    private static Comment createComment(User user, Advertisement advertisement, CommentCreateRequest commentCreateRequest) {
        return Comment.builder()
                .id(1L)
                .advertisement(advertisement)
                .childComments(new ArrayList<>())
                .user(user)
                .content(commentCreateRequest.getContent())
                .build();
    }
}