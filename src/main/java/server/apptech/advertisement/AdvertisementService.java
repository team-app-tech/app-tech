package server.apptech.advertisement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.event.domain.Event;
import server.apptech.file.FIleUploadService;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    private final UserRepository userRepository;

    private final FIleUploadService fIleUploadService;

    public Long createAdvertisement(AdCreateRequest adCreateRequest, MultipartFile[] multipartFiles) throws IOException {


        User tempUser = User.createTempuser();
        userRepository.save(tempUser);

        Event event = Event.of(adCreateRequest);

        Advertisement advertisement = Advertisement.of(adCreateRequest, event, tempUser);

        if(multipartFiles != null){
            for (MultipartFile multipartFile : multipartFiles){
                advertisement.addFile(fIleUploadService.saveFile(multipartFile));
            }
        }
        return advertisementRepository.save(advertisement).getId();
    }
}
