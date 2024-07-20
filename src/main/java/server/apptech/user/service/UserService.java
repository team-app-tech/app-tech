package server.apptech.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.advertisement.advertisementlike.domain.AdvertisementLike;
import server.apptech.advertisement.advertisementlike.domain.repository.AdvertisementLikeRepository;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.dto.AdResponse;
import server.apptech.auth.AuthUser;
import server.apptech.auth.Authority;
import server.apptech.file.FileService;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.login.domain.OauthUserInfo;
import server.apptech.user.controller.NickNameUpdateRequest;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementLikeRepository advertisementLikeRepository;

    private final FileService fIleUploadService;

    public User createUser(OauthUserInfo oauthUserInfo){
        return userRepository.save(User.of(oauthUserInfo));
    }

    public Optional<User> findByAuthId(String authId){
        return userRepository.findByAuthId(authId);
    }

    public User findByUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
    }

    public Long updateUserNickName(Long userId, NickNameUpdateRequest nickNameUpdateRequest) {
        System.out.println("userId: " + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        if(userRepository.existsByNickName(nickNameUpdateRequest.getNickName())){
            throw new RestApiException(ExceptionCode.ALREADY_EXIST_NICKNAME);
        }
        user.changeNickName(nickNameUpdateRequest.getNickName());
        return userRepository.save(user).getId();
    }

    public Long updateProfileImage(Long userId, MultipartFile multipartFile) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        if(user.getProfileImageUrl() != null){ //현재 이미지가 없지 않으면
            String currentProfileImageUrl = user.getProfileImageUrl();
            fIleUploadService.deleteFile(currentProfileImageUrl);
        }
        user.updateProfileImageUrl(fIleUploadService.saveFile(multipartFile).getUrl());
        return userRepository.save(user).getId();
    }

    public List<AdResponse> getMyAdvertisements(AuthUser user) {

        List<Advertisement> myAdvertisements = advertisementRepository.findWithUserByUserId(user.getUserId());

        List<Long> advertisementIds = myAdvertisements.stream()
                .map(Advertisement::getId)
                .collect(Collectors.toList());

        System.out.println(myAdvertisements.size());

        List<AdvertisementLike> likes = (user.getUserAuthority() != Authority.ROLE_VISITOR) ?
                advertisementLikeRepository.findByUserIdAndAdvertisementIdIn(user.getUserId(), advertisementIds) :
                Collections.emptyList();

        // 좋아요 정보를 Map으로 변환
        Map<Long, Boolean> likeMap = likes.stream()
                .collect(Collectors.toMap(
                        like -> like.getAdvertisement().getId(),
                        like -> true,
                        (v1, v2) -> v1
                ));
        return myAdvertisements.stream().map(ad -> {
            AdResponse adResponse = AdResponse.of(ad);
            adResponse.setIsLiked(likeMap.getOrDefault(ad.getId(), false));
            return adResponse;
        }).collect(Collectors.toList());
    }
}
