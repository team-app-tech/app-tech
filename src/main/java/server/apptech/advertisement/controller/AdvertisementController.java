package server.apptech.advertisement.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.advertisement.domain.type.SortOption;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.dto.AdResponse;
import server.apptech.advertisement.domain.type.EventStatus;
import server.apptech.advertisement.dto.PageAdResponse;
import server.apptech.advertisement.service.AdvertisementService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Advertisement")
public class AdvertisementController {

    public final AdvertisementService advertisementService;


    @PostMapping(value = "/api/advertisement", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE} )
    @Operation(summary = "광고 생성", description = "광고를 생성합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "정상적으로 생성")})
    public ResponseEntity<?> createAdvertisement(@RequestPart(value = "adCreateRequest") AdCreateRequest adCreateRequest, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Long advertisementId = advertisementService.createAdvertisement(adCreateRequest, multipartFiles);
        return ResponseEntity.ok()
                .body(advertisementId);
    }

    @Operation(summary = "광고글 목록 조회", description = "광고글 목록을 조회합니다",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상적으로 조회",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageAdResponse.class)))
            })
    @GetMapping("/api/advertisement")
    public ResponseEntity<?> getAdvertisements(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "eventStatus", required = false, defaultValue = "ONGOING") EventStatus eventStatus,
            @RequestParam(value = "sortOption", required = false, defaultValue = "PRIZE_DESCENDING") SortOption sortOption) {
        Page<AdResponse> adResponses =  advertisementService.getAdvertisements(page,size, eventStatus, sortOption);
        return ResponseEntity.ok()
                .body(PageAdResponse.of(adResponses));
    }

}
