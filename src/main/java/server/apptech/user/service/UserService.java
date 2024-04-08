package server.apptech.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.login.domain.OauthUserInfo;
import server.apptech.user.controller.NickNameUpdateRequest;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.User;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
