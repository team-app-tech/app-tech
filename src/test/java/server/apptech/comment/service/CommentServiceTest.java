package server.apptech.comment.service;

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
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.comment.dto.CommentUpdateRequest;
import server.apptech.comment.dto.PageCommentResponse;
import server.apptech.file.FileRepository;
import server.apptech.file.domain.File;
import server.apptech.file.domain.FileType;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock
    UserRepository userRepository;
    @Mock
    AdvertisementRepository advertisementRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    FileRepository fileRepository;

    @Test
    @DisplayName("정상적으로 댓글 생성후 댓글 아이디 반환(파일 없음)")
    void createComment() {
        //given
        User user = createUser();

        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        File file = craeteFile();

        CommentCreateRequest commentCreateRequest = createCommentRequestWithFile(file);
        Comment comment = createComment(user, advertisement, createCommentRequestWithFile(file));

        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(advertisementRepository.findById(any(Long.class))).willReturn(Optional.of(advertisement));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(fileRepository.findById(any(Long.class))).willReturn(Optional.of(file));

        //when
        Long commentId = commentService.createComment(user.getId(), advertisement.getId(), commentCreateRequest);

        //then
        verify(commentRepository, times(1)).save(any(Comment.class));
        assertThat(commentId).isEqualTo(1L);
    }

    @Test
    @DisplayName("광고 ID에 해당하는 댓글 조회")
    void getCommentsByAdvertisementId() {

        //given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        CommentCreateRequest commentCreateRequest = createCommentRequest();

        Comment comment = createComment(user, advertisement, commentCreateRequest);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        given(commentRepository.findCommentsByAdvertisementId(any(Long.class))).willReturn(comments);

        //when
        PageCommentResponse pageCommentResponse = commentService.getCommentsByAdvertisementId(advertisement.getId());

        //then
        assertThat(pageCommentResponse.getCommentResponses().size()).isEqualTo(comments.size());
        assertThat(pageCommentResponse.getCommentResponses().get(0).getChildComments()).isEqualTo(null);
    }

    @Test
    @DisplayName("광고 ID에 해당하는 댓글 조회(대댓글 포함 여부 확인)")
    void getCommentsWithChildCommentsByAdvertisementId() {

        //given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());
        Comment childComment = createChildComment(user, advertisement, comment);
        comment.addChildComment(childComment);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(childComment);

        given(commentRepository.findCommentsByAdvertisementId(any(Long.class))).willReturn(comments);
        //when
        PageCommentResponse pageCommentResponse = commentService.getCommentsByAdvertisementId(advertisement.getId());

        //then
        assertThat(pageCommentResponse.getCommentResponses().get(0).getChildComments().get(0).getCommentId()).isEqualTo(childComment.getId());
        assertThat(pageCommentResponse.getCommentResponses().size()).isEqualTo(1);
    }



    @Test
    @DisplayName("댓글 성공적으로 수정")
    void updateComment() {
        //given
        User user = createUser();

        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);

        CommentCreateRequest commentCreateRequest = createCommentRequest();
        Comment comment = createComment(user, advertisement, commentCreateRequest);
        CommentUpdateRequest commentUpdateRequest = createUpdateCommentRequest();

        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        //when
        Long commendId = commentService.updateComment(user.getId(), comment.getId(), commentUpdateRequest);

        //then
        verify(commentRepository, times(1)).save(any(Comment.class));
        assertThat(commendId).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재 하지 않는 댓글 수정시 예외 발생")
    void failUpdateCommentByNotFoundComment() {
        //given
        User user = createUser();

        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);

        CommentCreateRequest commentCreateRequest = createCommentRequest();
        Comment comment = createComment(user, advertisement, commentCreateRequest);
        CommentUpdateRequest commentUpdateRequest = createUpdateCommentRequest();

        given(commentRepository.findById(any(Long.class))).willThrow(new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));

        //when && then
        Assertions.assertThatThrownBy(() ->  commentService.updateComment(user.getId(),comment.getId(), commentUpdateRequest))
                .isInstanceOf(RestApiException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.NOT_FOUND_COMMENT);
    }

    @Test
    @DisplayName("본인이 작성하지 않은 댓글을 수정할 때 예외 발생")
    void failUpdateComment() {
        //given
        User user = createUser();
        User user2 = createUser2();

        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);

        Comment comment = createComment(user, advertisement, createCommentRequest());
        CommentUpdateRequest commentUpdateRequest = createUpdateCommentRequest();

        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));

        //when && then
        Assertions.assertThatThrownBy(() -> commentService.updateComment(user2.getId(), comment.getId(), commentUpdateRequest))
                .isInstanceOf(AuthException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.UNAUTHORIZED_USER_ACCESS);
    }

    @Test
    @DisplayName("존재하지 않는 댓글을 삭제할떄 때 예외 발생")
    void failDeleteCommentByNotFoundComment() {

        //given
        User user = createUser();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());

        given(commentRepository.findById(any(Long.class))).willThrow(new RestApiException(ExceptionCode.NOT_FOUND_COMMENT));

        //when && then
        Assertions.assertThatThrownBy(() -> commentService.deleteComment(user.getId(), comment.getId()))
                .isInstanceOf(RestApiException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.NOT_FOUND_COMMENT);
    }

    @Test
    @DisplayName("본인이 작성하지 않은 댓글을 삭제할떄 때 예외 발생")
    void failDeleteComment() {

        //given
        User user = createUser();
        User user2 = createUser2();
        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        Comment comment = createComment(user, advertisement, createCommentRequest());

        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));

        //when && then
        Assertions.assertThatThrownBy(() -> commentService.deleteComment(user2.getId(), comment.getId()))
                .isInstanceOf(AuthException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.UNAUTHORIZED_USER_ACCESS);
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
    private static CommentCreateRequest createCommentRequest() {
        return CommentCreateRequest.builder()
                .content("테스트 content")
                .build();
    }
    private static CommentCreateRequest createCommentRequestWithFile(File file) {
        return CommentCreateRequest.builder()
                .content("테스트 content")
                .fileId(file.getId())
                .build();
    }

    private static Comment createComment(User user, Advertisement advertisement, CommentCreateRequest commentCreateRequest) {
        return Comment.builder()
                .id(1L)
                .advertisement(advertisement)
                .childComments(new ArrayList<>())
                .commentLikes(new ArrayList<>())
                .user(user)
                .content(commentCreateRequest.getContent())
                .build();
    }

    private static File craeteFile() {
        File file = File.builder()
                .id(1L)
                .fileType(FileType.IMAGE_JPEG)
                .uuid("uuid")
                .url("url")
                .build();
        return file;
    }

    private static Comment createChildComment(User user, Advertisement advertisement, Comment comment) {
        return Comment.builder()
                .id(2L)
                .advertisement(advertisement)
                .user(user)
                .parent(comment)
                .childComments(new ArrayList<>())
                .commentLikes(new ArrayList<>())
                .content("대댓글")
                .build();
    }

    private static CommentUpdateRequest createUpdateCommentRequest() {
        return CommentUpdateRequest.builder().content("comment 변경").build();
    }
}

