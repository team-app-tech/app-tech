package server.apptech.advertisement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.advertisement.AdCreateRequest;
import server.apptech.event.domain.Event;
import server.apptech.file.domain.File;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "title")
    private String title;

    @Column(name="content")
    private String content;
    @Column(name = "view_cnt")
    private Integer viewCnt;

    @OneToMany(mappedBy = "advertisement")
    private List<File> files = new ArrayList<>();

    public static Advertisement of(AdCreateRequest adCreateRequest, Event event, User user){
        return Advertisement.builder()
                .title(adCreateRequest.getTitle())
                .user(user)
                .event(event)
                .files(new ArrayList<>())
                .content(adCreateRequest.getContent())
                .viewCnt(0)
                .build();
    }

    public void addFile(File file){
        files.add(file);
        file.belongToAdvertisement(this);
    }
}
