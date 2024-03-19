package server.apptech.advertisementlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisementlike.domain.repository.AdvertisementLikeRepository;
import server.apptech.advertisementlike.domain.AdvertisementLike;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertisementLikeService {
    private final AdvertisementLikeRepository advertisementLikeRepository;
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    public Long addAdvertisementLike(Long userId, Long advertisementId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_ADVERTISEMENT_ID));

        // 이미 누른 경우
        if(advertisementLikeRepository.existsByAdvertisementIdAndUserId(advertisementId, userId)){
            throw new RestApiException(ExceptionCode.ALREADY_LIKED_ADVERTISEMENT);
        }

        return advertisementLikeRepository.save(AdvertisementLike.of(advertisement, user)).getId();
    }

    public void cancelAdvertisementLike(Long userId, Long advertisementId) {
        userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        advertisementRepository.findById(advertisementId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_ADVERTISEMENT_ID));

        // 안 눌렀을 경우
        AdvertisementLike advertisementLike = advertisementLikeRepository.findByAdvertisementIdAndUserId(advertisementId, userId).orElseThrow(() -> new RestApiException(ExceptionCode.ADVERTISEMENT_NOT_LIKED));
        advertisementLikeRepository.delete(advertisementLike);
    }
}
