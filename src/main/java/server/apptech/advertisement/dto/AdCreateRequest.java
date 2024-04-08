package server.apptech.advertisement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdCreateRequest {

    @NotBlank(message = "광고의 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "광고의 내용을 입력해주세요.")
    private String content;
    @Min(value = 100, message = "상금금액은 100원 이상입니다.")
    private Long totalPrice;
    @Min(value = 1, message = "상금 수령인원은 1명 이상입니다.")
    private Integer prizeWinnerCnt;
    private String companyName;
    @NotNull(message = "광고의 썸네일 이미지는 필수 입니다.")
    private Long thumbNailImageId;
    @NotNull(message = "광고의 컨텐츠 이미지는 필수 입니다.")
    private Long contentImageId;
    @NotNull(message = "광고의 해당하는 결제 ID는 필수 입니다.")
    private Long paymentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

}
