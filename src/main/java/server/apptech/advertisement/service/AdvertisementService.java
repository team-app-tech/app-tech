package server.apptech.advertisement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.type.SortOption;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.dto.AdResponse;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.advertisement.domain.type.EventStatus;
import server.apptech.file.FIleUploadService;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    private final UserRepository userRepository;

    private final FIleUploadService fIleUploadService;

    public Long createAdvertisement(AdCreateRequest adCreateRequest, List<MultipartFile> multipartFiles) throws IOException {


        User tempUser = User.createTempuser();
        userRepository.save(tempUser);

        Advertisement advertisement = Advertisement.of(adCreateRequest, tempUser);

        if(multipartFiles != null){
            for (MultipartFile multipartFile : multipartFiles){
                advertisement.addFile(fIleUploadService.saveFile(multipartFile));
            }
        }
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
                break;
            case LIKES_DESCENDING:
                break;
            default:
                throw new RuntimeException("존재하지 않는 타입");
        }
        return advertisements.map(advertisement -> AdResponse.of(advertisement));
    }
}
