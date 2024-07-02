package server.apptech.advertisement.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.advertisement.domain.Advertisement;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdDetailResponse {

    private String nickName;
    private String title;
    private String content;
    private Long totalPrice;
    private Integer prizeWinnerCnt;
    private Integer viewCnt;
    private Integer commentCnt;
    private Integer likeCnt;
    private String fileUrl;
    private String authId;
    private Boolean isLiked;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    public static AdDetailResponse of (Advertisement advertisement){
        return AdDetailResponse.builder()
                .nickName(advertisement.getUser().getNickName())
                .title(advertisement.getTitle())
                .content(advertisement.getContent())
                .totalPrice(advertisement.getTotalPrice())
                .prizeWinnerCnt(advertisement.getPrizeWinnerCnt())
                .viewCnt(advertisement.getViewCnt())
                .likeCnt(advertisement.getLikeCnt())
                .commentCnt(advertisement.getCommentCnt())
                .startDate(advertisement.getStartDate())
                .endDate(advertisement.getEndDate())
                .createdDate(advertisement.getCreatedAt())
                .fileUrl(advertisement.getContentImage().getUrl())
                .isLiked(Boolean.FALSE)
                .authId(advertisement.getUser().getAuthId())
                .build();
    }
    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

}
