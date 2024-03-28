package server.apptech.commentlike.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.auth.Authority;
import server.apptech.comment.commentlike.service.CommentLikeService;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.comment.commentlike.domain.CommentLike;
import server.apptech.comment.commentlike.domain.repository.CommentLikeRepository;
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
class CommentLikeServiceTest {

    @InjectMocks
    CommentLikeService commentLikeService;
    @Mock
    CommentLikeRepository commentLikeRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("정성적으로 댓글에 좋아요 추가")
    void add_comment_ike() {
        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentLike commentLike = CommentLike.of(comment,user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        given(commentLikeRepository.existsByCommentIdAndUserId(comment.getId(), user.getId())).willReturn(Boolean.FALSE);
        given(commentLikeRepository.save(any(CommentLike.class))).willReturn(commentLike);
        // when
        Long commentLikeId= commentLikeService.addCommentLike(user.getId(), comment.getId());
        //then
        Assertions.assertThat(commentLikeId).isEqualTo(commentLike.getId());
    }

    @Test
    @DisplayName("이미 좋아요를 누른경우 예외발생")
    void fail_add_comment_ike() {
        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentLike commentLike = CommentLike.of(comment,user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        given(commentLikeRepository.existsByCommentIdAndUserId(comment.getId(), user.getId())).willReturn(Boolean.TRUE);

        // when && then
        Assertions.assertThatThrownBy(()-> commentLikeService.addCommentLike(user.getId(), comment.getId()))
                .isInstanceOf(RestApiException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.ALREADY_LIKED_COMMENT);
    }

    @Test
    @DisplayName("정상적으로 댓글 좋아요 취소")
    void cancel_comment_like() {
        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentLike commentLike = CommentLike.of(comment,user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        given(commentLikeRepository.findByCommentIdAndUserId(comment.getId(), user.getId())).willReturn(Optional.of(commentLike));
        // when
        commentLikeService.cancelCommentLike(user.getId(), comment.getId());
        //then
        verify(commentLikeRepository, times(1)).delete(commentLike);
    }

    @Test
    @DisplayName("좋아요를 누르지 않았는데 취소하려고 할시 예외발생")
    void fail_cancel_comment_like() {
        // given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        given(commentLikeRepository.findByCommentIdAndUserId(comment.getId(), user.getId())).willThrow(new RestApiException(ExceptionCode.COMMENT_NOT_LIKED));
        // when && then
        Assertions.assertThatThrownBy(()-> commentLikeService.cancelCommentLike(user.getId(), comment.getId()))
                        .isInstanceOf(RestApiException.class)
                        .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.COMMENT_NOT_LIKED);
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