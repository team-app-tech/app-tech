package server.apptech.advertisement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.type.SortOption;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.dto.AdResponse;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.domain.type.EventStatus;
import server.apptech.advertisement.dto.AdDetailResponse;
import server.apptech.file.FileRepository;
import server.apptech.file.domain.File;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.user.UserService;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final FileRepository fileRepository;
    private final UserService userService;

    public Long createAdvertisement(Long userId, AdCreateRequest adCreateRequest) {

        File thumbNailImage = fileRepository.findById(adCreateRequest.getThumbNailImageId()).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_IMAGE));
        File contentImage = fileRepository.findById(adCreateRequest.getContentImageId()).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_IMAGE));
        Advertisement advertisement = Advertisement.of(adCreateRequest, userService.findByUserId(userId), thumbNailImage, contentImage);

        return advertisementRepository.save(advertisement).getId();
    }

    public Page<AdResponse> getAdvertisements(int page, int size, EventStatus eventStatus, SortOption sortOption, String keyword) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<Advertisement> advertisements = null;

        switch (sortOption){
            case PRIZE_DESCENDING:
                if(eventStatus == EventStatus.UPCOMING){
                    advertisements =advertisementRepository.findByUpComingOrderByPrizeDesc(pageable,LocalDateTime.now(),keyword);

                }
                if(eventStatus == EventStatus.ONGOING){
                    advertisements =advertisementRepository.findByOnGoingOrderByPrizeDesc(pageable,LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.FINISHED){
                    advertisements = advertisementRepository.findByFinishedOrderByPrizeDesc(pageable,LocalDateTime.now(), keyword);
                }
                break;
            case START_ASCENDING:
                if(eventStatus == EventStatus.UPCOMING){
                    advertisements =advertisementRepository.findByUpComingOrderByStartDateAsc(pageable,LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.ONGOING){
                    advertisements =advertisementRepository.findByOnGoingOrderByStartDateAsc(pageable,LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.FINISHED){
                    advertisements =advertisementRepository.findByFinishedOrderByStartDateAsc(pageable,LocalDateTime.now(), keyword);
                }
                break;
            case END_ASCENDING:
                if(eventStatus == EventStatus.UPCOMING){
                    advertisements =advertisementRepository.findByUpComingOrderByEndDateAsc(pageable,LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.ONGOING){
                    advertisements =advertisementRepository.findByOnGoingOrderByEndDateAsc(pageable,LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.FINISHED){
                    advertisements =advertisementRepository.findByFinishedOrderByEndDateAsc(pageable,LocalDateTime.now(), keyword);
                }
                break;
            case VIEWS_DESCENDING:
                if(eventStatus == EventStatus.UPCOMING){
                    advertisements =  advertisementRepository.findByUpComingOrderByViewCnt(pageable, LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.ONGOING){
                    advertisements =  advertisementRepository.findByOngoingOrderByViewCnt(pageable, LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.FINISHED){
                    advertisements =  advertisementRepository.findByFinishedOrderByViewCnt(pageable, LocalDateTime.now(), keyword);
                }
                break;
            case COMMENTS_DESCENDING:
                if(eventStatus == EventStatus.UPCOMING){
                    advertisements =  advertisementRepository.findByUpComingOrderByCommentCnt(pageable, LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.ONGOING){
                    advertisements =  advertisementRepository.findByOngoingOrderCommentCnt(pageable, LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.FINISHED){
                    advertisements =  advertisementRepository.findByFinishedOrderCommentCnt(pageable, LocalDateTime.now(), keyword);
                }
                break;
            case LIKES_DESCENDING:
                if(eventStatus == EventStatus.UPCOMING){
                    advertisements =  advertisementRepository.findByUpComingOrderByLikeCnt(pageable, LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.ONGOING){
                    advertisements =  advertisementRepository.findByOngoingOrderByLikeCnt(pageable, LocalDateTime.now(), keyword);

                }
                if(eventStatus == EventStatus.FINISHED){
                    advertisements =  advertisementRepository.findByFinishedOrderByLikeCnt(pageable, LocalDateTime.now(), keyword);
                }
                break;
            default:
                throw new RestApiException(ExceptionCode.INVALID_SORT_OPTION);
        }
        return advertisements.map(advertisement -> AdResponse.of(advertisement));
    }

    public AdDetailResponse getAdvertisementById(Long advertisementId) {
        return AdDetailResponse.of(advertisementRepository.findWithUserById(advertisementId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_ADVERTISEMENT_ID)));
    }
}
