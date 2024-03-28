package server.apptech.advertisement.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.advertisement.domain.Advertisement;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdResponse {

    private Long advertisementId;
    private String title;
    private Long totalPrice;
    private Integer prizeWinnerCnt;

    private Integer viewCnt;
    private Integer commentCnt;
    private Integer likeCnt;

    private String fileUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    public static AdResponse of (Advertisement advertisement){
        return AdResponse.builder()
                .advertisementId(advertisement.getId())
                .title(advertisement.getTitle())
                .totalPrice(advertisement.getTotalPrice())
                .prizeWinnerCnt(advertisement.getPrizeWinnerCnt())
                .viewCnt(advertisement.getViewCnt())
                .likeCnt(advertisement.getLikeCnt())
                .commentCnt(advertisement.getCommentCnt())
                .startDate(advertisement.getStartDate())
                .endDate(advertisement.getEndDate())
                .fileUrl(advertisement.getThumbNailImage().getUrl())
                .build();
    }
}
