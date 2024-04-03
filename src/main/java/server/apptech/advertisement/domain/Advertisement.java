package server.apptech.advertisement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import server.apptech.advertisement.dto.AdUpdateRequest;
import server.apptech.advertisement.dto.AdCreateRequest;
import server.apptech.advertisement.advertisementlike.domain.AdvertisementLike;
import server.apptech.comment.domain.Comment;
import server.apptech.advertisement.domain.type.EventStatus;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "thumb_nail_image_id")
    private File thumbNailImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "content_image_id")
    private File contentImage;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdvertisementLike> advertisementLikes = new ArrayList<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @Formula("(SELECT count(*) FROM comment c WHERE c.ADVERTISEMENT_ID = id)")
    private int commentCnt;
    @Formula("(SELECT count(*) FROM advertisement_like al WHERE al.ADVERTISEMENT_ID = id)")
    private int likeCnt;

    public static Advertisement of(AdCreateRequest adCreateRequest, User user, File thumbNailImage, File contentImage){
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
                .thumbNailImage(thumbNailImage)
                .contentImage(contentImage)
                .advertisementLikes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void updateAdvertisement(AdUpdateRequest adUpdateRequest) {
        this.title = adUpdateRequest.getTitle();
        this.content = adUpdateRequest.getContent();
        this.prizeWinnerCnt = adUpdateRequest.getPrizeWinnerCnt();
        this.companyName = adUpdateRequest.getCompanyName();
        this.startDate = adUpdateRequest.getStartDate();
        this.endDate = adUpdateRequest.getEndDate();
    }

    public void changeThumbNailImage(File thumbNailImage) {
        this.thumbNailImage = thumbNailImage;
    }

    public void changeContentImage(File contentImage) {
        this.contentImage = contentImage;
    }
}
