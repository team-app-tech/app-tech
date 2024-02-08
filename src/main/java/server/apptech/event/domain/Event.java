package server.apptech.event.domain;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.global.domain.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    private Long totalPrice;
    private Integer prizeWinnerCnt;
    private String companyName;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private EventStatus eventStatus;

}
