package server.apptech.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.file.FileService;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.login.domain.OauthUserInfo;
import server.apptech.user.controller.NickNameUpdateRequest;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.User;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
