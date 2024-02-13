package server.apptech.advertisement;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AdvertisementController {

    public final AdvertisementService advertisementService;

    @PostMapping("/api/advertisement")
    public ResponseEntity<?> createAdvertisement(@RequestPart(value = "adCreateRequest") AdCreateRequest adCreateRequest, @RequestPart(value = "file", required = false)MultipartFile[] multipartFiles) throws IOException {
        Long advertisementId = advertisementService.createAdvertisement(adCreateRequest, multipartFiles);
        return ResponseEntity.ok(advertisementId);
    }


}
