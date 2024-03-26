package server.apptech.advertisement.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.dto.AdDetailResponse;
import server.apptech.file.FIleUploadService;
import server.apptech.file.domain.File;
import server.apptech.file.domain.FileType;
import server.apptech.user.UserRepository;
import server.apptech.user.UserService;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;
import server.apptech.auth.Authority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    @InjectMocks
    AdvertisementService advertisementService;
    @Mock
    AdvertisementRepository advertisementRepository;
    @Mock
    UserService userService;
    @Mock
    FIleUploadService fIleUploadService;
    
    @Test
    @DisplayName("광고가 정상 저장된후 아이디 반환(이미지 없음)")
    void createAdvertisement() throws IOException {
        //given
        AdCreateRequest adCreateRequest = createtAdCreateRequest();
        User user = createUser();
        Advertisement advertisement = createAdvertisement(adCreateRequest, user);
        File file = craeteFile();
        MockMultipartFile mockFile = createMockMultipartFile();
        List<MultipartFile> multipartFiles = Collections.singletonList(mockFile);

        given(advertisementRepository.save(any(Advertisement.class))).willReturn(advertisement);
        given(userService.findByUserId(any(Long.class))).willReturn(user);
        given(fIleUploadService.saveFile(any(MultipartFile.class))).willReturn(file);

        //when
        Long advertisementId = advertisementService.createAdvertisement(user.getId(), adCreateRequest, multipartFiles);

        //then
        verify(advertisementRepository, times(1)).save(any(Advertisement.class));
        assertThat(advertisementId).isEqualTo(1L);
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
        Advertisement advertisement = createAdvertisement(adCreateRequest, user);
        given(advertisementRepository.findWithUserById(any(Long.class))).willReturn(Optional.of(advertisement));

        //when
        AdDetailResponse adDetailResponse = advertisementService.getAdvertisementById(1L);

        //then
        Assertions.assertThat(AdDetailResponse.of(advertisement)).usingRecursiveComparison()
                .isEqualTo(adDetailResponse);
    }

    private static MockMultipartFile createMockMultipartFile() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",           // parameter name
                "test_file",       // original file name
                "IMAGE/JPEG",      // content type
                "Mock file content".getBytes() // content as bytes
        );
        return mockFile;
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