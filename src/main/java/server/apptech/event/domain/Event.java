package server.apptech.event.domain;


import jakarta.persistence.*;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.global.domain.BaseEntity;

import java.time.LocalDateTime;

@Entity
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "prize_winner_cnt")
    private Integer prizeWinnerCnt;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "event_status")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

}
