package server.apptech.advertisement;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdCreateRequest {

    private String title;
    private String content;

    private Long totalPrice;
    private Integer prizeWinnerCnt;
    private String companyName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
