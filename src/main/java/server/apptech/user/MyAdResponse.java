package server.apptech.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.advertisement.domain.Advertisement;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyAdResponse {

    private Long advertisementId;
    private String title;
    private Long totalPrice;
    private Integer prizeWinnerCnt;

    private Integer viewCnt;
    private Integer commentCnt;
    private Integer likeCnt;

    private String fileUrl;
    private String authId;

    private Boolean isLiked;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    public static server.apptech.advertisement.dto.AdResponse of (Advertisement advertisement){
        return server.apptech.advertisement.dto.AdResponse.builder()
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
                .authId(advertisement.getUser().getAuthId())
                .build();
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}

