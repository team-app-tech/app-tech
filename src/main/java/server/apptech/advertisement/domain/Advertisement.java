package server.apptech.advertisement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.advertisement.AdCreateRequest;
import server.apptech.advertisementlike.domain.AdvertisementLike;
import server.apptech.comment.comment.Comment;
import server.apptech.event.domain.EventStatus;
import server.apptech.file.domain.File;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Advertisement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name="content")
    private String content;
    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "prize_winner_cnt")
    private Integer prizeWinnerCnt;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @Column(name = "event_status")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<AdvertisementLike> advertisementLikes = new ArrayList<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public static Advertisement of(AdCreateRequest adCreateRequest, User user){
        return Advertisement.builder()
                .user(user)
                .title(adCreateRequest.getTitle())
                .content(adCreateRequest.getContent())
                .viewCnt(0)
                .totalPrice(adCreateRequest.getTotalPrice())
                .prizeWinnerCnt(adCreateRequest.getPrizeWinnerCnt())
                .companyName(adCreateRequest.getCompanyName())
                .startDate(adCreateRequest.getStartDate())
                .endDate(adCreateRequest.getEndDate())
                .files(new ArrayList<>())
                .advertisementLikes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    public void addFile(File file){
        files.add(file);
        file.belongToAdvertisement(this);
    }
}
