package server.apptech.advertisement;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.event.domain.EventStatus;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdvertisementController {

    public final AdvertisementService advertisementService;

    @PostMapping("/api/advertisement")
    public ResponseEntity<?> createAdvertisement(@RequestPart(value = "adCreateRequest") AdCreateRequest adCreateRequest, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Long advertisementId = advertisementService.createAdvertisement(adCreateRequest, multipartFiles);
        return ResponseEntity.ok()
                .body(advertisementId);
    }

    @GetMapping("/api/advertisement")
    public ResponseEntity<?> getAdvertisements(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "eventStatus", required = false, defaultValue = "ONGOING") EventStatus eventStatus,
            @RequestParam(value = "sortOption", required = false, defaultValue = "PRIZE_DESCENDING") SortOption sortOption) {

        Page<AdResponse> adResponses =  advertisementService.getAdvertisements(page,size, eventStatus, sortOption);
        return ResponseEntity.ok()
                .body(adResponses);
    }

}
