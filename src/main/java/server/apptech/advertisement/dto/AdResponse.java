package server.apptech.advertisement.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.advertisement.domain.Advertisement;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdResponse {

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

    //후에 getAdvertisementLikes().size(), getComments().size() 수정
    public static AdResponse of (Advertisement advertisement){
        return AdResponse.builder()
                .title(advertisement.getTitle())
                .totalPrice(advertisement.getTotalPrice())
                .prizeWinnerCnt(advertisement.getPrizeWinnerCnt())
                .viewCnt(advertisement.getViewCnt())
                .likeCnt(advertisement.getAdvertisementLikes().size())
                .commentCnt(advertisement.getComments().size())
                .startDate(advertisement.getStartDate())
                .endDate(advertisement.getEndDate())
                .fileUrl(advertisement.getFiles().stream().map(file -> file.getUrl()).findAny().orElse("null"))
                .build();
    }
}
