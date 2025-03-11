package server.apptech.advertisement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.advertisement.advertisementlike.domain.AdvertisementLike;
import server.apptech.advertisement.advertisementlike.domain.repository.AdvertisementLikeRepository;
import server.apptech.advertisement.dto.AdUpdateRequest;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.type.SortOption;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.dto.AdResponse;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.domain.type.EventStatus;
import server.apptech.advertisement.dto.AdDetailResponse;
import server.apptech.auth.AuthUser;
import server.apptech.auth.Authority;
import server.apptech.file.FileRepository;
import server.apptech.file.domain.File;
import server.apptech.global.exception.InvalidPaymentException;
import server.apptech.global.scheduler.PrizeScheduler;
import server.apptech.global.exception.AuthException;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.payment.domain.Payment;
import server.apptech.payment.domain.repository.PaymentRepository;
import server.apptech.user.domain.repository.UserRepository;
import server.apptech.user.domain.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final PrizeScheduler prizeScheduler;
    private final PaymentRepository paymentRepository;
    private final AdvertisementLikeRepository advertisementLikeRepository;

    public Long createAdvertisement(Long userId, AdCreateRequest adCreateRequest) {

        File thumbNailImage = fileRepository.findById(adCreateRequest.getThumbNailImageId()).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_IMAGE));
        File contentImage = fileRepository.findById(adCreateRequest.getContentImageId()).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_IMAGE));
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));
        Payment payment = paymentRepository.findById(adCreateRequest.getPaymentId()).orElseThrow(() -> new InvalidPaymentException(ExceptionCode.NOT_FOUND_PAYMENT));

        Advertisement advertisement = Advertisement.of(adCreateRequest, user, thumbNailImage, contentImage, payment);

        Long advertisementId = advertisementRepository.save(advertisement).getId();

        prizeScheduler.reservePrizeDistributionTask(advertisementId,advertisement.getEndDate());
        return advertisementId;
    }

    public Page<AdResponse> getAdvertisements(AuthUser user, int page, int size, EventStatus eventStatus, SortOption sortOption, String keyword) {

        PageRequest pageable = PageRequest.of(page, size);

        // 광고 가져오기
        Page<Advertisement> advertisements = advertisementRepository.findAdvertisements(pageable, eventStatus, sortOption, LocalDateTime.now(), keyword);

        List<Long> advertisementIds = advertisements.getContent().stream()
                .map(Advertisement::getId)
                .collect(Collectors.toList());

        // 현재 사용자가 누른 좋아요 조회
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

        // AdResponse로 변환 및 좋아요 정보 설정
        return advertisements.map(ad -> {
            AdResponse adResponse = AdResponse.of(ad);
            adResponse.setIsLiked(likeMap.getOrDefault(ad.getId(), false));
            return adResponse;
        });
    }

    public AdDetailResponse getAdvertisementById(AuthUser user, Long advertisementId) {

        Advertisement advertisement = advertisementRepository.findWithUserById(advertisementId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_ADVERTISEMENT_ID));
        advertisement.plusViewCnt();
        AdDetailResponse adDetailResponse = AdDetailResponse.of(advertisement);
        if(user.getUserAuthority() != Authority.ROLE_VISITOR && advertisementLikeRepository.existsByAdvertisementIdAndUserId(advertisementId, user.getUserId())){
            adDetailResponse.setIsLiked(Boolean.TRUE);
        }
        return adDetailResponse;
    }

    public Long updateAdvertisement(Long userId, Long advertisementId, AdUpdateRequest adUpdateRequest) {
        Advertisement advertisement = advertisementRepository.findWithUserById(advertisementId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_ADVERTISEMENT_ID));
        if(advertisement.getUser().getId() != userId){
            throw new AuthException(ExceptionCode.UNAUTHORIZED_USER_ACCESS);
        }
        checkIfAdvertisementModifiable(advertisement);
        advertisement.updateAdvertisement(adUpdateRequest);
        handleFileUpdate(adUpdateRequest, advertisement);
        prizeScheduler.modifyPrizeDistributionTask(advertisementId, adUpdateRequest.getEndDate());
        Long updatedId = advertisementRepository.save(advertisement).getId();
        return updatedId;
    }

    private void handleFileUpdate(AdUpdateRequest adUpdateRequest, Advertisement advertisement) {
        File thumbNailImage = fileRepository.findById(adUpdateRequest.getThumbNailImageId()).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_IMAGE));
        File contentImage = fileRepository.findById(adUpdateRequest.getContentImageId()).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_IMAGE));
        if(advertisement.getThumbNailImage().getId() != thumbNailImage.getId()){
            advertisement.changeThumbNailImage(thumbNailImage);
        }
        if(advertisement.getContentImage().getId() != contentImage.getId()){
            advertisement.changeContentImage(contentImage);
        }
    }

    private void checkIfAdvertisementModifiable(Advertisement advertisement) {
        if(advertisement.getStartDate().isBefore(LocalDateTime.now())) {
            throw new RestApiException(ExceptionCode.ALREADY_START_ADVERTISEMENT);
        }
    }
}
