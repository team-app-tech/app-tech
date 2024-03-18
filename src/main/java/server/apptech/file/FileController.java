package server.apptech.file;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.auth.Auth;
import server.apptech.auth.AuthUser;
import server.apptech.comment.dto.PageCommentResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FIleUploadService fIleUploadService;

    @PostMapping(value = "/api/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    @Operation(summary = "이미지 저장", description = "이미지를 저장합니다.", responses = {@ApiResponse(responseCode = "200", description = "정상적으로 생성 fileId 값을 반환합니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))})
    public ResponseEntity<Long> saveImage(@Auth AuthUser authUser,
                                                 @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok()
                .body(fIleUploadService.saveFile(multipartFile).getId());
    }
}
