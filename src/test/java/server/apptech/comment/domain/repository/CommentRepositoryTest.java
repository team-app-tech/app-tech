package server.apptech.comment.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.auth.Authority;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.file.FileRepository;
import server.apptech.file.domain.File;
import server.apptech.file.domain.FileType;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CommentRepositoryTest {


    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;


    @Test
    void save() {

        //given
        User user = createUser();

        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        File file = craeteFile();
        CommentCreateRequest commentCreateRequest = createCommentRequestWithFile(file);
        Comment comment = createComment(user, advertisement, createCommentRequestWithFile(file));

        //when
        Comment saveComment = commentRepository.save(comment);

        //then
        assertThat(saveComment.getId()).isEqualTo(comment.getId());
    }
    @Test
    @DisplayName("advertisementId에 해당하는 comment 조회")
    void findCommentsByAdvertisementId() {


        //given
        User user = createUser();

        Advertisement advertisement = createAdvertisement(createtAdCreateRequest(), user);
        File file = craeteFile();
        CommentCreateRequest commentCreateRequest = createCommentRequestWithFile(file);
        Comment comment = createComment(user, advertisement, createCommentRequestWithFile(file));

        fileRepository.save(file);
        userRepository.save(user);
        advertisementRepository.save(advertisement);
        commentRepository.save(comment);

        //when
        List<Comment> commentsByAdvertisementId = commentRepository.findCommentsByAdvertisementId(advertisement.getId());

        //then
        Assertions.assertThat(commentsByAdvertisementId.size()).isEqualTo(1);
        Assertions.assertThat(commentsByAdvertisementId.get(0).getId()).isEqualTo(comment.getId());
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
    private static CommentCreateRequest createCommentRequestWithFile(File file) {
        return CommentCreateRequest.builder()
                .content("테스트 content")
                .fileId(file.getId())
                .build();
    }

    private static Comment createComment(User user, Advertisement advertisement, CommentCreateRequest commentCreateRequest) {
        return Comment.builder()
                .advertisement(advertisement)
                .user(user)
                .content(commentCreateRequest.getContent())
                .build();
    }

    private static File craeteFile() {
        File file = File.builder()
                .fileType(FileType.IMAGE_JPEG)
                .uuid("uuid")
                .url("url")
                .build();
        return file;
    }
}