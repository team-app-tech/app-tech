package server.apptech.advertisement.service;

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
import server.apptech.advertisement.dto.AdDetailResponse;
import server.apptech.file.FileRepository;
import server.apptech.file.domain.File;
import server.apptech.file.domain.FileType;
import server.apptech.user.UserService;
import server.apptech.user.domain.SocialType;
import server.apptech.user.domain.User;
import server.apptech.auth.Authority;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
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
    UserService userService;
    @Mock
    FileRepository fileRepository;
    @Test
    @DisplayName("광고가 정상 저장된후 아이디 반환(이미지 없음)")
    void saveAdvertisement() throws IOException {
        //given
        AdCreateRequest adCreateRequest = createtAdCreateRequest();
        User user = createUser();
        File file = createFile();
        File file2 = createFile2();
        Advertisement advertisement = createAdvertisement(adCreateRequest, user, file, file2);

        given(advertisementRepository.save(any(Advertisement.class))).willReturn(advertisement);
        given(userService.findByUserId(any(Long.class))).willReturn(user);
        given(fileRepository.findById(file.getId())).willReturn(Optional.of(file));
        given(fileRepository.findById(file2.getId())).willReturn(Optional.of(file2));

        //when
        Long advertisementId = advertisementService.createAdvertisement(user.getId(), adCreateRequest);

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
        File file = createFile();
        File file2 = createFile2();
        Advertisement advertisement = createAdvertisement(adCreateRequest, user,file, file2);
        given(advertisementRepository.findWithUserById(any(Long.class))).willReturn(Optional.of(advertisement));

        //when
        AdDetailResponse adDetailResponse = advertisementService.getAdvertisementById(1L);

        //then
        Assertions.assertThat(AdDetailResponse.of(advertisement)).usingRecursiveComparison()
                .isEqualTo(adDetailResponse);
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
                .thumbNailImageId(1L)
                .contentImageId(2L)
                .build();
        return adCreateRequest;
    }

}